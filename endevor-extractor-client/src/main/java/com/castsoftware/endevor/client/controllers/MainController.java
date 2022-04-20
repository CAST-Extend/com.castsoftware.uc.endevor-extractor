package com.castsoftware.endevor.client.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.filechooser.FileSystemView;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.castsoftware.endevor.ResponseData;
import com.castsoftware.endevor.pojo.Element;
import com.castsoftware.endevor.RestUtil;
import com.castsoftware.endevor.pojo.Instance;
import com.castsoftware.endevor.pojo.SubSys;
import com.castsoftware.endevor.pojo.Sys;
import com.castsoftware.endevor.pojo.Type;
import com.castsoftware.endevor.tasks.Extractor;
import com.castsoftware.utils.FileUtil;
import com.castsoftware.utils.GlobalProperties;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements KnowStage 
{
	private static final Logger LOGGER = Logger.getLogger(MainController.class);
	
	@FXML private HBox hbSteps;
	@FXML private Label lConnection;
	@FXML private Label lSelection;
	@FXML private Label lAction;
	
	@FXML private AnchorPane apSliding;
	
	@FXML private TextField tfUrl;
	@FXML private TextField tfLogin;
	@FXML private PasswordField pfPassword;
	@FXML private Button bConnect;
	
	@FXML private ComboBox<Instance> cbInstances;
	@FXML private ComboBox<String> cbEnv;
	@FXML private ComboBox<String> cbStg;
	@FXML private ComboBox<String> cbSys;
	@FXML private ComboBox<String> cbSubSys;
	@FXML private ComboBox<String> cbType;
	@FXML private TextField tfFilter;
	@FXML private Button bNext;
	
	@FXML private TextArea taContent;
	@FXML private ProgressBar progressBar;
	@FXML private Button bExtractAll;
	@FXML private Button bGenerateBatch;
	
	private Stage stage;
	
	//Transitions
    TranslateTransition ttSliding;
    private final int pageWidth = 380;
		
	private ObservableList<Instance> instanceList = FXCollections.observableArrayList();
	private ObservableList<String> envList = FXCollections.observableArrayList();
	private ObservableList<String> stgList = FXCollections.observableArrayList();
	private ObservableList<String> sysList = FXCollections.observableArrayList();
	private ObservableList<String> subSysList = FXCollections.observableArrayList();
	private ObservableList<String> typeList = FXCollections.observableArrayList();
	private ObservableList<Element> elementList = FXCollections.observableArrayList();
	
	private BooleanProperty hasElements = new SimpleBooleanProperty(false);
	private BooleanProperty extracting = new SimpleBooleanProperty(false);
	
	private Timeline delayedChangeFilter = new Timeline(new KeyFrame(
	        Duration.millis(500),
	        ae -> filterChanged()));
	
	private Boolean freezeElementsReload = false;
	
	private Timeline backgroundTimer = new Timeline();
	
	private enum WizardSteps {wsConnection, wsSelection, wsAction}
	private WizardSteps currentStep = WizardSteps.wsConnection;
	
	//Account for undecided users
	Task<ResponseData<List<Sys>>> latestEnvStgTask;
	Task<ResponseData<List<Sys>>> latestSysTask;
	Task<ResponseData<List<SubSys>>> latestSubSysTask;
	Task<ResponseData<List<Type>>> latestTypeTask;
	Task<ResponseData<List<Element>>> latestElementTask;
	
	@FXML
	private void initialize()
	{		
		ttSliding = new TranslateTransition(new Duration(500), apSliding);
		
		try {
			tfUrl.setText(GlobalProperties.getInstance().getPropertyValue("url"));
			tfLogin.setText(GlobalProperties.getInstance().getPropertyValue("login"));
			pfPassword.setText(GlobalProperties.getInstance().getPropertyValue("password"));
		} catch (InterruptedException | IOException e) {
			LOGGER.error(e);
		}		
		
		//Extract Progress Bar
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), e -> updateUI());
		backgroundTimer.getKeyFrames().add(keyFrame);
		backgroundTimer.setCycleCount(Timeline.INDEFINITE);
		
		cbInstances.setItems(instanceList);
		cbInstances.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> changeInstance(newValue));

		cbEnv.setItems(envList);
		cbEnv.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> changeEnv(newValue));
		cbEnv.disableProperty().bind(Bindings.isNull(cbInstances.getSelectionModel().selectedItemProperty()));
		
		cbStg.setItems(stgList);
		cbStg.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> changeStgNum(newValue));
		cbStg.disableProperty().bind(Bindings.isNull(cbInstances.getSelectionModel().selectedItemProperty()));
		
		cbSys.setItems(sysList);
		cbSys.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> changeSys(newValue));
		cbSys.disableProperty().bind(Bindings.or(
				Bindings.isNull(cbEnv.getSelectionModel().selectedItemProperty()),
				Bindings.isNull(cbStg.getSelectionModel().selectedItemProperty())));
		
		cbSubSys.setItems(subSysList);
		cbSubSys.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> changeSubSys(newValue));
		cbSubSys.disableProperty().bind(Bindings.isNull(cbSys.getSelectionModel().selectedItemProperty()));
		
		cbType.setItems(typeList);
		cbType.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> changeType(newValue));
		cbType.disableProperty().bind(Bindings.isNull(cbSys.getSelectionModel().selectedItemProperty()));
		
		tfFilter.textProperty().addListener((observable, oldValue, newValue) -> resetFilterChangeTimer());
		tfFilter.disableProperty().bind(Bindings.isNull(cbType.getSelectionModel().selectedItemProperty()));
		
		bExtractAll.disableProperty().bind(Bindings.or(hasElements.not(), extracting));
		lAction.disableProperty().bind(hasElements.not());
		bNext.disableProperty().bind(hasElements.not());
		
		goToConnection();
	}
	
	@Override
	public void setStage(Stage stage){
    	this.stage = stage;
    }
	
	private Boolean connectAndListInstances()
	{		
		try {
			GlobalProperties.getInstance().setPropertyValue("url", tfUrl.getText());
			GlobalProperties.getInstance().setPropertyValue("login", tfLogin.getText());
			GlobalProperties.getInstance().setPropertyValue("password", pfPassword.getText());
		} catch (InterruptedException | IOException e) {
			LOGGER.error(e);
		}
		

		Instance selectedInstance = cbInstances.getSelectionModel().getSelectedItem();
		instanceList.clear();

		//Load Instance
		List<Instance> newInstanceList = new ArrayList<Instance>();
		Response response = RestUtil.listInstances(tfLogin.getText(), pfPassword.getText(), 
				tfUrl.getText(), newInstanceList);
	
		if (response != null)
			if (response.getStatus() == 200)
			{
				instanceList.addAll(newInstanceList);
				cbInstances.getSelectionModel().select(selectedInstance);
				return true;
			}
			else
			{
				LOGGER.warn(String.format("Loading instances failed with return code %d.\n%s", 
						response.getStatus(), response.readEntity(String.class)));
				alertOnBadReturnCode(String.format("Loading instances failed with return code %d.\n%s", 
						response.getStatus(), response.readEntity(String.class)));
			}
		else
			connectionFailed();

		return false;
	}
	
	private void changeInstance(Instance newInstance)
	{
		if (newInstance == null)
			return;
		
		//Load env and stgnum
		reloadEnvAndStg();
	}
	
	private void reloadEnvAndStg()
	{
		//The following REST queries are not implemented yet
		//{instance}/env/ -> returns the list of env
		//{instance}/env/*/stgnum/ -> returns the list of stgnum for a/all env

		//The ugly way to list env and stgnum is to extract the information from
		//{instance}/env/*/stgnum/*/sys/
		
		stage.getScene().setCursor(Cursor.WAIT);
		
		String selectedEnv = cbEnv.getSelectionModel().getSelectedItem();
		String selectedStg = cbStg.getSelectionModel().getSelectedItem();
		envList.clear();
		stgList.clear();

		Task<ResponseData<List<Sys>>> task = new Task<ResponseData<List<Sys>>>() {
		    @Override
		    public ResponseData<List<Sys>> call() {
		    	return RestUtil.listSystems(tfLogin.getText(), pfPassword.getText(), 
						tfUrl.getText(), cbInstances.getSelectionModel().getSelectedItem().getName(), "*", "*");
		    }
		};
		task.setOnSucceeded(e -> {
			//User changed his/her mind while task was already running -> discard these results
			if (latestEnvStgTask != task)
				return;
			
			stage.getScene().setCursor(Cursor.DEFAULT);
			ResponseData<List<Sys>> responseData = task.getValue();
			if (responseData.getResponse() != null && responseData.getResponse().getStatus() == 200)
			{
				envList.addAll(responseData.getResponseObject().data.stream()
					.map(s -> s.getEnvName())
					.distinct()
					.filter(s -> s != null)
					.collect(Collectors.toList()));
				envList.sort(Comparator.comparing(String::toString));
				envList.add(0, "*");
				
				stgList.addAll(responseData.getResponseObject().data.stream()
						.map(s -> s.getStgId())
						.distinct()
						.filter(s -> s != null)
						.collect(Collectors.toList()));
				stgList.sort(Comparator.comparing(String::toString));
				stgList.add(0, "*");
				
				cbEnv.getSelectionModel().select(selectedEnv);
				cbStg.getSelectionModel().select(selectedStg);
			}
			else
			{
				LOGGER.warn(String.format("Loading environments and stages failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
				alertOnBadReturnCode(String.format("Loading environments and stages failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
			}
		});
		latestEnvStgTask = task;
		new Thread(task).start();
	}
	
	private void changeEnv(String newEnv)
	{
		if (newEnv == null)
			return;
		
		if (cbStg.getSelectionModel().getSelectedItem() == null)
			return;
		
		//Load System
		reloadSystems();
	}
	
	private void changeStgNum(String newStgNum)
	{
		if (newStgNum == null)
			return;
		
		if (cbEnv.getSelectionModel().getSelectedItem() == null)
			return;
		
		//Load System
		reloadSystems();
	}
	
	private void reloadSystems()
	{
		stage.getScene().setCursor(Cursor.WAIT);
		
		String selectedSys = cbSys.getSelectionModel().getSelectedItem();
		sysList.clear();

		Task<ResponseData<List<Sys>>> task = new Task<ResponseData<List<Sys>>>() {
		    @Override
		    public ResponseData<List<Sys>> call() {
		    	return RestUtil.listSystems(tfLogin.getText(), pfPassword.getText(), 
						tfUrl.getText(), cbInstances.getSelectionModel().getSelectedItem().getName(),
						cbEnv.getSelectionModel().getSelectedItem(), 
						cbStg.getSelectionModel().getSelectedItem());
		    }
		};
		task.setOnSucceeded(e -> {
			//User changed his/her mind while task was already running -> discard these results
			if (latestSysTask != task)
				return;
			
			stage.getScene().setCursor(Cursor.DEFAULT);
			ResponseData<List<Sys>> responseData = task.getValue();
			if (responseData.getResponse() != null && responseData.getResponse().getStatus() == 200)
			{
				sysList.addAll(responseData.getResponseObject().data.stream()
						.map(s -> s.getSysName())
						.distinct()
						.filter(s -> s != null)
						.collect(Collectors.toList()));
				sysList.add(0, "*");
				
				cbSys.getSelectionModel().select(selectedSys);
			}
			else
			{
				LOGGER.warn(String.format("Loading systems failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
				alertOnBadReturnCode(String.format("Loading systems failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
			}
		});
		latestSysTask = task;
		new Thread(task).start();
	}
	
	private void changeSys(String newSys)
	{
		if (newSys == null)
			return;
		
		//Load SubSystem
		freezeElementsReload = true;
		reloadSubSystems();
		freezeElementsReload = false;
		//Load Types
		reloadTypes();
	}
	
	private void reloadSubSystems()
	{
		stage.getScene().setCursor(Cursor.WAIT);
		
		String selectedSubSys = cbSubSys.getSelectionModel().getSelectedItem();
		subSysList.clear();

		Task<ResponseData<List<SubSys>>> task = new Task<ResponseData<List<SubSys>>>() {
		    @Override
		    public ResponseData<List<SubSys>> call() {
		    	return RestUtil.listSubSystems(tfLogin.getText(), pfPassword.getText(), 
						tfUrl.getText(), cbInstances.getSelectionModel().getSelectedItem().getName(),
						cbEnv.getSelectionModel().getSelectedItem(), 
						cbStg.getSelectionModel().getSelectedItem(), 
						cbSys.getSelectionModel().getSelectedItem());
		    }
		};
		task.setOnSucceeded(e -> {
			//User changed his/her mind while task was already running -> discard these results
			if (latestSubSysTask != task)
				return;
			
			stage.getScene().setCursor(Cursor.DEFAULT);
			ResponseData<List<SubSys>> responseData = task.getValue();
			if (responseData.getResponse() != null && responseData.getResponse().getStatus() == 200)
			{
				subSysList.addAll(responseData.getResponseObject().data.stream()
						.map(s -> s.getSbsName())
						.distinct()
						.filter(s -> s != null)
						.collect(Collectors.toList()));
				subSysList.add(0, "*");
				
				cbSubSys.getSelectionModel().select(selectedSubSys);
			}
			else
			{
				LOGGER.warn(String.format("Loading sub-systems failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
				alertOnBadReturnCode(String.format("Loading sub-systems failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
			}
		});
		latestSubSysTask = task;
		new Thread(task).start();
	}
	
	private void reloadTypes()
	{
		stage.getScene().setCursor(Cursor.WAIT);
		
		String selectedType = cbType.getSelectionModel().getSelectedItem();
		typeList.clear();

		Task<ResponseData<List<Type>>> task = new Task<ResponseData<List<Type>>>() {
		    @Override
		    public ResponseData<List<Type>> call() {
		    	return RestUtil.listTypes(tfLogin.getText(), pfPassword.getText(), 
						tfUrl.getText(), cbInstances.getSelectionModel().getSelectedItem().getName(),
						cbEnv.getSelectionModel().getSelectedItem(), 
						cbStg.getSelectionModel().getSelectedItem(), 
						cbSys.getSelectionModel().getSelectedItem());
		    }
		};
		task.setOnSucceeded(e -> {
			//User changed his/her mind while task was already running -> discard these results
			if (latestTypeTask != task)
				return;

			stage.getScene().setCursor(Cursor.DEFAULT);
			ResponseData<List<Type>> responseData = task.getValue();
			if (responseData.getResponse() != null && responseData.getResponse().getStatus() == 200)
			{
				typeList.addAll(responseData.getResponseObject().data.stream()
						.map(s -> s.getTypeName())
						.distinct()
						.filter(s -> s != null)
						.collect(Collectors.toList()));
				typeList.add(0, "*");
				
				cbType.getSelectionModel().select(selectedType);
			}
			else
			{
				LOGGER.warn(String.format("Loading types failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
				alertOnBadReturnCode(String.format("Loading types failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
			}
		});
		latestTypeTask = task;
		new Thread(task).start();
	}
	
	private void changeSubSys(String newSubSys)
	{
		if (newSubSys == null)
			return;
		
		if (freezeElementsReload)
			return;
		
		//Load Elements
		reloadElements();
	}
	
	private void changeType(String newType)
	{
		if (newType == null)
			return;
		
		//Load Elements
		reloadElements();
	}
	
	private void resetFilterChangeTimer() 
	{
		delayedChangeFilter.stop();
		delayedChangeFilter.play();
	}
	
	private void filterChanged()
	{
		delayedChangeFilter.stop();
		reloadElements();
	}
	
	private void reloadElements()
	{
		if (cbSubSys.getSelectionModel().getSelectedItem() == null || 
				cbType.getSelectionModel().getSelectedItem() == null)
			return;
		
		stage.getScene().setCursor(Cursor.WAIT);

		elementList.clear();
		hasElements.setValue(false);

		Task<ResponseData<List<Element>>> task = new Task<ResponseData<List<Element>>>() {
		    @Override
		    public ResponseData<List<Element>> call() {
		    	return RestUtil.listElements(tfLogin.getText(), pfPassword.getText(), 
						tfUrl.getText(), cbInstances.getSelectionModel().getSelectedItem().getName(),
						cbEnv.getSelectionModel().getSelectedItem(), 
						cbStg.getSelectionModel().getSelectedItem(), 
						cbSys.getSelectionModel().getSelectedItem(),
						cbSubSys.getSelectionModel().getSelectedItem(),
						cbType.getSelectionModel().getSelectedItem(),
						tfFilter.getText().isEmpty() ? "*" : tfFilter.getText()
						);
		    }
		};
		task.setOnSucceeded(e -> {
			//User changed his/her mind while task was already running -> discard these results
			if (latestElementTask != task)
				return;
			
			stage.getScene().setCursor(Cursor.DEFAULT);
			ResponseData<List<Element>> responseData = task.getValue();
			if (responseData.getResponse() != null && responseData.getResponse().getStatus() == 200)
			{
				elementList.addAll(responseData.getResponseObject().data);
				hasElements.setValue(elementList.size() > 0);
				elementsSummary();
			}
			else
			{
				LOGGER.warn(String.format("Loading elements failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
				alertOnBadReturnCode(String.format("Loading elements failed with return code %d.\n%s", 
	            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
			}
		});
		latestElementTask = task;
		new Thread(task).start();
	}
	
	private void elementsSummary() 
	{		
		String summary = "Content to be extracted:";
		for(String type : typeList)
		{
			long cnt = elementList.stream().filter(e -> e.getTypeName().equals(type)).count();
			if (cnt > 0)
				summary += String.format("\n - %s : %d", type, cnt);
		}
		
		taContent.setText(summary);
	}
	
	@FXML private void onPanelDoubleClick(MouseEvent mouseEvent)
	{
		if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
            if(mouseEvent.getClickCount() == 2)
            	openRawQuery();
	}
	
	private void openRawQuery()
	{
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode;
		try {
			rootNode = (Parent) loader.load(getClass().getResourceAsStream("/fxml/RawQuery.fxml"));
			RawQueryController rawQueryController = loader.getController();
			Stage dialogStage = new Stage();
			rawQueryController.setStage(dialogStage);

	        Scene scene = new Scene(rootNode);
	        scene.getStylesheets().add("/styles/styles.css");

	        dialogStage.setTitle("Raw Query");
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        dialogStage.initModality(Modality.APPLICATION_MODAL);
	        
	        dialogStage.show();
		} catch (IOException e) {
			LOGGER.error("Failed to open RawQuery window", e);
		}

	}
	
	private String chooseExtractDirectory()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose Output Location");
		
		String extractFolder = null;
		try {
			extractFolder = GlobalProperties.getInstance().getPropertyValue("exportFolder");
		} catch (InterruptedException | IOException e) {
			LOGGER.error(e);
		}
		
		if (extractFolder == null || extractFolder.isEmpty() || !FileUtil.fileExists(extractFolder))
			extractFolder = FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath();
		directoryChooser.setInitialDirectory(new File(extractFolder));
			

		File selectedFile = directoryChooser.showDialog(stage);
		if (selectedFile != null) 
		{		 
			extractFolder = selectedFile.getAbsolutePath().replaceAll("\\\\", "/");;
			if (!extractFolder.endsWith("/"))
				extractFolder += "/";
		    
			try {
				GlobalProperties.getInstance().setPropertyValue("exportFolder", extractFolder);
			} catch (InterruptedException | IOException e) {
				LOGGER.error(e);
			}
			return extractFolder;
		}
		else
			return null;
	}
	
	@FXML private void onExtractAll()
	{
		progressBar.setVisible(true);
		
		String extractFolder = chooseExtractDirectory();
		if (extractFolder != null) 
		{		 
	    	Extractor.start();
	    	//Start a monitor thread
	        backgroundTimer.play();
	        extracting.set(true);
	        
		    for (Element element : elementList)
		    	Extractor.newExtractTask(tfUrl.getText(), tfLogin.getText(), pfPassword.getText(), 
						cbInstances.getSelectionModel().getSelectedItem().getName(),
						element.getEnvName(), element.getStgNum(), element.getSysName(), element.getSbsName(),
						element.getTypeName(), element.getElmName(), extractFolder);
		}
	}
	
	@FXML private void onGenerateBatch()
	{
		String extractFolder = chooseExtractDirectory();
		if (extractFolder != null) 
		{	
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Batch file to");
			
			fileChooser.setInitialDirectory(new File(FileUtil.getCurrentFolder()));
			fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("CMD Batch", "*.cmd"),
	                new FileChooser.ExtensionFilter("BAT Batch", "*.bat")
	            );
			File selectedFile = fileChooser.showSaveDialog(stage);
			if (selectedFile != null) 
			{
				try(BufferedWriter writer = Files.newBufferedWriter(selectedFile.toPath(), Charset.forName("UTF-8")))
				{
					String batch = String.format("set JAR_PATH=%s\\", FileUtil.getCurrentFolder());
					batch += "\r\n";
					batch += String.format("\r\nset URL=%s", tfUrl.getText());
					batch += String.format("\r\nset LOGIN=%s", tfLogin.getText());
					batch += String.format("\r\nset PASSWORD=%s", pfPassword.getText());
					batch += "\r\n";
					batch += String.format("\r\nset INSTANCE=%s", cbInstances.getSelectionModel().getSelectedItem().getName());
					batch += String.format("\r\nset ENV=%s", cbEnv.getSelectionModel().getSelectedItem());
					batch += String.format("\r\nset STAGE=%s", cbStg.getSelectionModel().getSelectedItem());
					batch += String.format("\r\nset FOLDER=%s", extractFolder);
					batch += "\r\n";
					String optionalParameters = "";
					if (!cbSys.getSelectionModel().getSelectedItem().equals("*"))
					{
						batch += String.format("\r\nset SYSTEM=%s", cbSys.getSelectionModel().getSelectedItem());
						optionalParameters = " -system %SYSTEM%";
					}
					
					if (!cbSubSys.getSelectionModel().getSelectedItem().equals("*"))
					{
						batch += String.format("\r\nset SUBSYSTEM=%s", cbSubSys.getSelectionModel().getSelectedItem());
						optionalParameters += " -sbs %SUBSYSTEM%";
					}
					
					if (!cbType.getSelectionModel().getSelectedItem().equals("*"))
					{
						batch += String.format("\r\nset TYPE=%s", cbType.getSelectionModel().getSelectedItem());
						optionalParameters += " -type %TYPE%";
					}
					
					if (!tfFilter.getText().isEmpty())
					{
						batch += String.format("\r\nset FILTER=%s", tfFilter.getText());
						optionalParameters += " -filter %FILTER%";
					}
					
					batch += "\r\n";
					batch += String.format("\r\njava -jar \"%%JAR_PATH%%endevor-extractor.jar\" -url %%URL%% -login %%LOGIN%% -password %%PASSWORD%% -instance %%INSTANCE%% -env \"%%ENV%%\" -stage \"%%STAGE%%\" %s -folder \"%%FOLDER%%\"", optionalParameters);
					writer.write(batch);
				}catch(IOException e){
					LOGGER.error("Failed to create batch file", e);
				}
			}
		}
	}
	
	private void updateUI()
	{
		if (!Extractor.isRunning())
		{
			backgroundTimer.stop();
			progressBar.setVisible(false);
			extracting.set(false);
		}
		
		progressBar.setProgress(Extractor.getProgress());
	}
	
	public Boolean canShutdown()
    { 
    	Extractor.shutdown();
    	
    	//Give Extractor time to shutdown!!!
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
		}
    	
    	if (Extractor.isShutdown())
    		return true;
    	else
    	{
    		Alert alert = 
        	        new Alert(AlertType.WARNING, 
        	            "Tasks are still running. Are you sure you want to stop them? ",
        	             ButtonType.YES, 
        	             ButtonType.NO);
        	alert.setTitle("Closing Application");
        	Optional<ButtonType> result = alert.showAndWait();
        	
        	if (result.get() == ButtonType.YES) 
        	{
        		Extractor.shutdownNow();        		
        		return true;
        	}
        	else
        		return false;
    	}    	
    }
	
	private void alertOnBadReturnCode(String message)
	{
		Alert alert = 
    	        new Alert(AlertType.WARNING, message, ButtonType.OK);
    	alert.setTitle("Unexpected Return Code");
    	alert.show();
	}
	
	private void connectionFailed()
	{
		Alert alert = 
    	        new Alert(AlertType.ERROR, "Connection to REST server failed!", ButtonType.OK);
    	alert.setTitle("Connection Failed");
    	alert.showAndWait();
	}
	
	private void setSelectedLabel(Label label)
	{
		hbSteps.getChildren().stream().forEach(n -> n.getStyleClass().remove("selected-step"));
		label.getStyleClass().add("selected-step");
	}
	
	@FXML private void onConnectAction() 
	{
		if (currentStep == WizardSteps.wsConnection)
		{
			if (connectAndListInstances())
				goToSelection();
		}
		else
			goToSelection();
				
	}
	
	@FXML private void goToConnection() 
	{
		currentStep = WizardSteps.wsConnection;
		setSelectedLabel(lConnection);
		
		ttSliding.setToX(0);
		ttSliding.play();
	}
	
	@FXML private void goToSelection() 
	{
		currentStep = WizardSteps.wsSelection;
		setSelectedLabel(lSelection);
		
		ttSliding.setToX(-pageWidth);
		ttSliding.play();
	}
	
	@FXML private void goToAction() 
	{
		currentStep = WizardSteps.wsAction;
		setSelectedLabel(lAction);
		
		ttSliding.setToX(-2*pageWidth);
		ttSliding.play();
	}
}
