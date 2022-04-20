package com.castsoftware.endevor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.castsoftware.endevor.pojo.Element;
import com.castsoftware.endevor.pojo.Instance;
import com.castsoftware.endevor.pojo.ResponseObject;
import com.castsoftware.endevor.pojo.SubSys;
import com.castsoftware.endevor.pojo.Sys;
import com.castsoftware.endevor.pojo.Type;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class RestUtil {
	private static final Logger LOGGER = Logger.getLogger(RestUtil.class);
	private static Gson gson = new Gson();
	
	public static Response query(String login, String password, String url, String path, 
			String parameters, String mediaType)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug(String.format("Url: %s", url));
			LOGGER.debug(String.format("Path: %s", path));
			LOGGER.debug(String.format("Parameters: %s", parameters));
			LOGGER.debug(String.format("Media Type: %s", mediaType));
		}
		
		Client client = ClientBuilder.newClient();
		
		HttpAuthenticationFeature authentication = HttpAuthenticationFeature
				.basic(login, password);	
		client.register(authentication);
		
		WebTarget target = client.target(url);
		
		WebTarget specificTarget = target.path(path == null ? "" : path);
		
		if (parameters != null && !parameters.isEmpty())
			for(String param : parameters.split("&"))
				if (param.contains("="))
				{
					specificTarget = specificTarget.queryParam(param.split("=")[0], param.split("=")[1]);
				}
		
		Invocation.Builder invocationBuilder = specificTarget
				.request()
				.accept(mediaType); 
		
		try
		{
		Response response =  invocationBuilder.get();
		response.bufferEntity();
		
		return response;
		} catch (Exception e) {
			LOGGER.error("Failed to Connect", e);
		}
		return null;
	}
	
	public static Response listInstances(String login, String password, String url, 
			List<Instance> instanceList)
	{
		Response response = query(login, password, url, null, null, MediaType.APPLICATION_JSON);
		
		if (response != null && response.getStatus() == 200 && response.hasEntity())
		{
            java.lang.reflect.Type collectionType = new TypeToken<Collection<Instance>>(){}.getType();
            Collection<Instance> tmp = gson.fromJson(response.readEntity(String.class), collectionType);
            instanceList.addAll(tmp);
            
            instanceList.sort(Comparator.comparing(Instance::getName));
		}
		
		return response;
	}

	public static ResponseData<List<Sys>> listSystems(String login, String password, String url, 
			String instance, String env, String stgNum)
	{
		Response response = query(login, password, url, 
				String.format("%s/env/%s/stgnum/%s/sys/*", instance, env, stgNum), 
				null, MediaType.APPLICATION_JSON);
		
		ResponseObject<List<Sys>> responseObject = null;
		if (response != null && response.getStatus() == 200 && response.hasEntity())
		{
            java.lang.reflect.Type responseObjectType = new TypeToken<ResponseObject<List<Sys>>>(){}.getType();
            responseObject = gson.fromJson(response.readEntity(String.class), responseObjectType);
            
            responseObject.data.sort(Comparator.comparing(Sys::getSysName));
		}
		
		return new ResponseData<List<Sys>>(response, responseObject);
	}

	public static ResponseData<List<SubSys>> listSubSystems(String login, String password, String url, 
			String instance, String env, String stgNum, String sys)
	{
		Response response = query(login, password, url, 
				String.format("%s/env/%s/stgnum/%s/sys/%s/subsys/*", instance, env, stgNum, sys), 
				null, MediaType.APPLICATION_JSON);
		
		ResponseObject<List<SubSys>> responseObject = null;
		if (response != null && response.getStatus() == 200 && response.hasEntity())
		{
            java.lang.reflect.Type responseObjectType = new TypeToken<ResponseObject<List<SubSys>>>(){}.getType();
            responseObject = gson.fromJson(response.readEntity(String.class), responseObjectType);
			
            responseObject.data.sort(Comparator.comparing(SubSys::getSbsName));
		}
		
		return new ResponseData<List<SubSys>>(response, responseObject);
	}

	public static ResponseData<List<Type>> listTypes(String login, String password, String url, 
			String instance, String env, String stgNum, String sys)
	{
		Response response = query(login, password, url, 
				String.format("%s/env/%s/stgnum/%s/sys/%s/type/*", instance, env, stgNum, sys), 
				null, MediaType.APPLICATION_JSON);
		
		ResponseObject<List<Type>> responseObject = null;
		if (response != null && response.getStatus() == 200 && response.hasEntity())
		{
            java.lang.reflect.Type responseObjectType = new TypeToken<ResponseObject<List<Type>>>(){}.getType();
            responseObject = gson.fromJson(response.readEntity(String.class), responseObjectType);            
            
            responseObject.data.sort(Comparator.comparing(Type::getTypeName));
		}
		
		return new ResponseData<List<Type>>(response, responseObject);
	}
	
	public static ResponseData<List<Element>> listElements(String login, String password, String url, 
			String instance, String env, String stgNum, String sys, String subSys, String type, String filter)
	{
		Response response = query(login, password, url, 
				String.format("%s/env/%s/stgnum/%s/sys/%s/subsys/%s/type/%s/ele/%s", instance, env, stgNum, sys, subSys, type, filter), 
				null, MediaType.APPLICATION_JSON);
		
		ResponseObject<List<Element>> responseObject = null;
		if (response != null && response.getStatus() == 200 && response.hasEntity())
		{
            java.lang.reflect.Type responseObjectType = new TypeToken<ResponseObject<List<Element>>>(){}.getType();
            try
            {
            	responseObject = gson.fromJson(response.readEntity(String.class), responseObjectType);
                
            	if (responseObject.data != null)
    	            responseObject.data.sort(Comparator.comparing(Element::getEnvName)
    	            		.thenComparing(Comparator.comparing(Element::getStgId).reversed())
    	            		.thenComparing(Comparator.comparing(Element::getSysName))
    	            		.thenComparing(Comparator.comparing(Element::getSbsName))
    	            		.thenComparing(Comparator.comparing(Element::getTypeName))
    	            		.thenComparing(Comparator.comparing(Element::getElmName)));

            } catch (JsonSyntaxException e) {
            	LOGGER.error(String.format("Unexpected json format:\n%s", response.readEntity(String.class)), e);
            }
			
		}
		
		return new ResponseData<List<Element>>(response, responseObject);
	}
	
	public static ResponseData<List<String>> retrieveElement(String login, String password, String url, 
			String instance, String env, String stgNum, String sys, String subSys, String type, String ele,
			Boolean expandIncludes)
	{
		Response response = query(login, password, url, 
				String.format("%s/env/%s/stgnum/%s/sys/%s/subsys/%s/type/%s/ele/%s", 
						instance, env, stgNum, sys, subSys, type, ele), 
						"noSignout=yes" + (expandIncludes ? "&expandIncludes=yes" : ""), 
						MediaType.APPLICATION_OCTET_STREAM);
		
		ResponseObject<List<String>> responseObject = null;
		if (response.hasEntity())
		{
			if (response != null && response.getStatus() == 200 && response.getMediaType().toString().equals(MediaType.APPLICATION_OCTET_STREAM))
			{
				InputStream inputStream = response.readEntity(InputStream.class);
				StringBuilder textBuilder = new StringBuilder();
			    try (Reader reader = new BufferedReader(new InputStreamReader
			      (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
			        int c = 0;
			        while ((c = reader.read()) != -1) {
			            textBuilder.append((char) c);
			        }
			    } catch (IOException e) {
					e.printStackTrace();
				}
			    responseObject = new ResponseObject<List<String>>();
			    responseObject.data = new ArrayList<String>();
			    responseObject.data.add(textBuilder.toString());
			}
			else
			{
	            responseObject = new ResponseObject<List<String>>();
			    responseObject.data = new ArrayList<String>();
			    responseObject.data.add(response.readEntity(String.class));
			}
		}			
		
		return new ResponseData<List<String>>(response, responseObject);
	}
}
