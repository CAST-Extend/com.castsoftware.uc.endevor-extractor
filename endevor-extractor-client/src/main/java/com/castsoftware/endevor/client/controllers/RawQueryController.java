package com.castsoftware.endevor.client.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.castsoftware.endevor.RestUtil;
import com.castsoftware.utils.GlobalProperties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RawQueryController implements KnowStage 
{

	@FXML private TextField tfPath;
	@FXML private TextField tfQueryParameters;
	@FXML private ComboBox<String> cbMediaType;
	@FXML private TextArea taResults;
	
	@SuppressWarnings("unused")
	private Stage stage;

	private ObservableList<String> mediaTypeList = FXCollections.observableArrayList();

	@FXML
	private void initialize()
	{
		mediaTypeList.add(MediaType.APPLICATION_ATOM_XML);
		mediaTypeList.add(MediaType.APPLICATION_FORM_URLENCODED);
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		mediaTypeList.add(MediaType.APPLICATION_JSON_PATCH_JSON);
		mediaTypeList.add(MediaType.APPLICATION_OCTET_STREAM);
		mediaTypeList.add(MediaType.APPLICATION_SVG_XML);
		mediaTypeList.add(MediaType.APPLICATION_XHTML_XML);
		mediaTypeList.add(MediaType.APPLICATION_XML);
		mediaTypeList.add(MediaType.CHARSET_PARAMETER);
		mediaTypeList.add(MediaType.MEDIA_TYPE_WILDCARD);
		mediaTypeList.add(MediaType.MULTIPART_FORM_DATA);
		mediaTypeList.add(MediaType.SERVER_SENT_EVENTS);
		mediaTypeList.add(MediaType.TEXT_HTML);
		mediaTypeList.add(MediaType.TEXT_PLAIN);
		mediaTypeList.add(MediaType.TEXT_XML);
		mediaTypeList.add(MediaType.WILDCARD);
		cbMediaType.setItems(mediaTypeList);
		cbMediaType.getSelectionModel().select(MediaType.APPLICATION_JSON);
	}
	
	@Override
	public void setStage(Stage stage) {
		this.stage = stage;		
	}

	@FXML private void onQueryAction() 
	{
		try
		{
			taResults.setText("");
			Response response = RestUtil.query(
					GlobalProperties.getInstance().getPropertyValue("login"),
					GlobalProperties.getInstance().getPropertyValue("password"),
					GlobalProperties.getInstance().getPropertyValue("url"),
					tfPath.getText(), tfQueryParameters.getText(), 
					cbMediaType.getSelectionModel().getSelectedItem());		
	
			if (response.hasEntity())
			{
				if (cbMediaType.getSelectionModel().getSelectedItem().equals(MediaType.APPLICATION_OCTET_STREAM))
				{
					InputStream inputStream = response.readEntity(InputStream.class);
					StringBuilder textBuilder = new StringBuilder();
				    try (Reader reader = new BufferedReader(new InputStreamReader
				      (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
				        int c = 0;
				        while ((c = reader.read()) != -1) {
				            textBuilder.append((char) c);
				        }
				    }
					
					taResults.setText(String.format("%d - %s\nMedia Type: %s\nContent Length: %d\nResponse:\n%s", 
							response.getStatus(), 
							response.getStatusInfo().toString(),
							response.getMediaType().toString(),
							response.getLength(),
							textBuilder.toString()));
				}
				else
					taResults.setText(String.format("%d - %s\nMedia Type: %s\nContent Length: %d\nResponse:\n%s",
							response.getStatus(),
							response.getStatusInfo().toString(),
							response.getMediaType().toString(),
							response.getLength(),
							response.readEntity(String.class)));
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
