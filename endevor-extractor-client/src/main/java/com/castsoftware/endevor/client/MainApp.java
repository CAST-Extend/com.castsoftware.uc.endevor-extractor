package com.castsoftware.endevor.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.castsoftware.endevor.ResponseData;
import com.castsoftware.endevor.RestUtil;
import com.castsoftware.endevor.TypeToExtension;
import com.castsoftware.endevor.client.controllers.MainController;
import com.castsoftware.endevor.pojo.Element;
import com.castsoftware.endevor.tasks.Extractor;
import com.castsoftware.utils.FileUtil;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

	private static final Logger LOGGER = Logger.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception 
    {
    	//Log4j
    	if (args.length > 0)
    	{
    		initializePropertiesFile("log4j-batch.properties");
	    	PropertyConfigurator.configure("log4j-batch.properties");
	    	LOGGER.info("Log4j (Batch) Initialized");
    	}
    	else
    	{
	    	initializePropertiesFile("log4j.properties");
	    	PropertyConfigurator.configure("log4j.properties");
	    	LOGGER.info("Log4j Initialized");
	    	
	    	//App properties
	    	initializePropertiesFile("endevor-extractor-client.properties");
	    	try {
				com.castsoftware.utils.GlobalProperties.getInstance().setPropertyFile("endevor-extractor-client.properties");
			} catch (InterruptedException e) {
				LOGGER.error(e);
			}
    	}
    	
    	//TypeToExtension Mappings
    	initializePropertiesFile("TypeToExtension.properties");
    	TypeToExtension.load("TypeToExtension.properties");
    	
    	if (args.length > 0)
        {
    		Map<String, String> parameters = new HashMap<String, String>();
        	
    		String previousArg = null;
    		for(String arg : args)
    		{
    			if (arg.startsWith("-"))
    				parameters.put(arg.substring(1), null);
    			else
    				parameters.put(previousArg.substring(1), arg);
    			previousArg = arg;
    		}
    		
    		if (!parameters.containsKey("system"))
    			parameters.put("system", "*");
    		if (!parameters.containsKey("sbs"))
    			parameters.put("sbs", "*");
    		if (!parameters.containsKey("type"))
    			parameters.put("type", "*");
    		if (!parameters.containsKey("filter"))
    			parameters.put("filter", "*");
    		
    		//-url http://141.202.36.6:47401/EndevorService/rest -login GODJU02 -password endvca06 -instance ENDEVOR -env DEV -stage 1 -system FINANCE -folder C:\Users\jgd\Documents\CA-Extract
    		//check that all mandatory parameters are present. If not display help
    		int taskCount = 0;
			if (parameters.containsKey("url") && parameters.containsKey("login") && parameters.containsKey("password") &&
    			parameters.containsKey("instance") && parameters.containsKey("env") && parameters.containsKey("stage") &&
    			parameters.containsKey("folder"))
    		{
	    		String extractFolder = parameters.get("folder").replaceAll("\\\\", "/");
				if (!extractFolder.endsWith("/"))
					extractFolder += "/";
				parameters.put("folder", extractFolder);
				
    			DisplayParameters(parameters);
    			//run extraction
    			taskCount = ExtractFiles(parameters);
    			
    	   		//Wait for extract thread to complete
    			long remaining = taskCount;
            	do 
            	{
        	    	try {
        				Thread.sleep(1000);
        				long r = Extractor.getRemaining();
        				if (r != remaining && r > 0)
        					LOGGER.info(String.format("%d / %d", taskCount - r, taskCount));
        				remaining = r;
        			} catch (InterruptedException e1) {
        			}
            	}
        	    while (Extractor.isRunning());       	
    			LOGGER.info("Extraction completed");
    		}
    		else
    			DisplayHelp();
			
        	//Shutdown Extractor
			LOGGER.info("Shutting down...");
        	Extractor.shutdown();
        	
        	//Give Extractor time to shutdown!!!
        	try {
    			Thread.sleep(200);
    		} catch (InterruptedException e1) {
    		}
        	
        	//Force shutdown if necessary
        	if (Extractor.isShutdown())
        	{
        		LOGGER.info("Shut down");
        		System.exit(0);
        	}
        	else
        	{
        		Extractor.shutdownNow();
        		LOGGER.info("Shut down");
        		System.exit(0);
        	}
        }
        else
        {
        	launch(args);
        }
    }
    
    private static int ExtractFiles(Map<String, String> parameters) 
    {
    	ResponseData<List<Element>> responseData;

		responseData = RestUtil.listElements(parameters.get("login"), parameters.get("password"), 
				parameters.get("url"), parameters.get("instance"), parameters.get("env"), parameters.get("stage"), 
				parameters.get("system"), parameters.get("sbs"), parameters.get("type"), parameters.get("filter"));

	
    	if (responseData.getResponse() != null && responseData.getResponse().getStatus() == 200)
		{
    		Extractor.start();
    		
    		LOGGER.info("Content to be extracted:");
    		for(String type : responseData.getResponseObject().data.stream()
					    			.map(e -> e.getTypeName())
					    			.distinct()
					    			.filter(s -> s != null)
					    			.collect(Collectors.toList()))
    		{
    			long cnt = responseData.getResponseObject().data.stream()
    							.filter(e -> e.getTypeName().equals(type))
    							.count();
    			if (cnt > 0)
    				LOGGER.info(String.format(" - %s : %d", type, cnt));
    		}    		

			LOGGER.info("Extracting files...");
    		for(Element element : responseData.getResponseObject().data)
    			Extractor.newExtractTask(parameters.get("url"), parameters.get("login"), parameters.get("password"), 
    					parameters.get("instance"), element.getEnvName(), element.getStgNum(), 
    					element.getSysName(), element.getSbsName(), element.getTypeName(), 
    					element.getElmName(), parameters.get("folder"));
    		return responseData.getResponseObject().data.size();
		}
		else
		{
			LOGGER.error(String.format("Loading elements failed with return code %d.\n%s", 
            		responseData.getResponse().getStatus(), responseData.getResponse().readEntity(String.class)));
			return 0;
		}
    }
    
    private static void DisplayParameters(Map<String, String> parameters)
    {
    	LOGGER.info("Parameters:");
    	LOGGER.info(String.format("-url: %s", parameters.get("url")));
    	LOGGER.info(String.format("-login: %s", parameters.get("login")));
    	LOGGER.info(String.format("-password: %s", "**********"));
    	LOGGER.info(String.format("-instance: %s", parameters.get("instance")));
    	LOGGER.info(String.format("-env: %s", parameters.get("env")));
    	LOGGER.info(String.format("-stage: %s", parameters.get("stage")));
    	LOGGER.info(String.format("-folder: %s", parameters.get("folder")));
    	LOGGER.info("");
    	LOGGER.info(String.format("-system: %s", parameters.get("system")));
    	LOGGER.info(String.format("-sbs: %s", parameters.get("sbs")));
    	LOGGER.info(String.format("-type: %s", parameters.get("type")));
    	LOGGER.info(String.format("-filter: %s", parameters.get("filter")));
    	LOGGER.info("");
    }
    
    private static void DisplayHelp()
    {
    	LOGGER.info("Parameters:");
    	LOGGER.info("-url <url>              Endevor REST API Url (Mandatory)");
    	LOGGER.info("-login <login>          Endevor REST API Login (Mandatory)");
    	LOGGER.info("-password <password>    Endevor REST API Login (Mandatory)");
    	LOGGER.info("-instance <instance>    Instance name (Mandatory)");
    	LOGGER.info("-env <env>              Environment (Mandatory)");
    	LOGGER.info("-stage <stage>          Stage (Mandatory)");
    	LOGGER.info("-folder <folder>        Target Folder (Mandatory)");
    	LOGGER.info("");
    	LOGGER.info("-system <system> [*]    System (Default: *)");
    	LOGGER.info("-sbs <sbs> [*]          Sub-System (Default: *)");
    	LOGGER.info("-type <type> [*]        Type (Default: *)");
    	LOGGER.info("-filter <filter> [*]    Filter elements by name (Default: *)");
    }
    
    private static void initializePropertiesFile(String fileName)
    {
    	if (!FileUtil.fileExists(fileName))
		{
			InputStream is = MainApp.class.getResourceAsStream("/ini/" + fileName);
			try {
				FileUtil.exportResource(is, fileName);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}	
    }    

    public void start(Stage stage) throws Exception 
    {    		
    	String fxmlFile = "/fxml/Main.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        MainController mainController = loader.getController();
        mainController.setStage(stage);

        Scene scene = new Scene(rootNode);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Endevor Extractor");
        stage.setScene(scene);
        stage.setResizable(false);
        
        Platform.setImplicitExit(false);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
            	if (!mainController.canShutdown())
            		event.consume();
            	else
            	{
            		TypeToExtension.save("TypeToExtension.properties");
            		System.exit(0);
            	}
            }
        });
        
        stage.show();
    }
}
