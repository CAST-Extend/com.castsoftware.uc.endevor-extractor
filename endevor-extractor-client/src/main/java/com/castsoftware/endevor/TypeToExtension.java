package com.castsoftware.endevor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Properties;

public class TypeToExtension {
	private static final Logger LOGGER = Logger.getLogger(TypeToExtension.class);
	
	private static Map<String, String> map = new HashMap<String, String>();
	
	public static void load(String fileName)
	{
		Properties extensionMapping = new Properties();
		FileInputStream in = null;
		try
		{
			in = new FileInputStream(fileName);
			extensionMapping.load(in);
		} catch (IOException e) {
			LOGGER.error("Failed to load extension map", e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				LOGGER.error("Failed to close inputstream, after loading extension map", e);
			}
		}
		
		for(String property : extensionMapping.stringPropertyNames())
			map.put(property, extensionMapping.getProperty(property));
	}
	
	public static void save(String fileName) 
	{
		Properties extensionMapping = new Properties();
		
		for(Entry<String, String> kv : map.entrySet())
			extensionMapping.setProperty(kv.getKey(), kv.getValue());
		
		try {
			extensionMapping.store(new FileOutputStream(fileName), null);
		} catch (IOException e) {
			LOGGER.error(String.format("Failed to save extension map to '%s'", fileName), e);
		}
	}
	
	public static String getExtension(String type) {
		return map.get(type) == null ? "" : "." + map.get(type);
	}
	
	/*
	COBOL program - COB or CBL
	COBOL copybook - CPY or COP
	JCL job - JCL
	JCL procedure - PRC
	IMS PSB - PSB
	IMS DBD - DBD
	CICS Mapset - BMS
	CICS CSD flat file - CSD, CICS or TXT
	*/
}
