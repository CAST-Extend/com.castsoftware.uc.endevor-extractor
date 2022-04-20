package com.castsoftware.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GlobalProperties {
	
	private static class Holder 
	{
		public static final GlobalProperties INSTANCE = new GlobalProperties();
	}

	public static GlobalProperties getInstance(){
        return Holder.INSTANCE;
    }
	
	private final Lock lock = new ReentrantLock();
	private String propertyFile;
	
	public void setPropertyValue(String propertyName, String propertyValue) throws InterruptedException, IOException
	{
		try
		{
			lock.tryLock(10, TimeUnit.SECONDS);
			Properties applicationProps = new Properties();
			FileInputStream in;
			in = new FileInputStream(propertyFile);
			applicationProps.load(in);
			in.close();
			
			applicationProps.setProperty(propertyName, propertyValue);
			applicationProps.store(new FileOutputStream(propertyFile), null);
		}
		finally
		{
			lock.unlock();
		}			
	}
	
	public String getPropertyValue(String propertyName) throws InterruptedException, IOException
	{
		try
		{
			lock.tryLock(10, TimeUnit.SECONDS);
			Properties applicationProps = new Properties();
			FileInputStream in;
			in = new FileInputStream(propertyFile);
			applicationProps.load(in);
			in.close();
			
			return applicationProps.getProperty(propertyName);
		} 
		finally
		{
			lock.unlock();
		}				
	}
	
	public int getPropertyValueAsInteger(String propertyName, int defaultValue) throws InterruptedException, IOException
	{
		try
		{
			lock.tryLock(10, TimeUnit.SECONDS);
			Properties applicationProps = new Properties();
			FileInputStream in;
			in = new FileInputStream(propertyFile);
			applicationProps.load(in);
			in.close();
			
			return Integer.parseInt(applicationProps.getProperty(propertyName));
		} catch (NumberFormatException e)
		{
			return defaultValue;
		}
		finally
		{
			lock.unlock();
		}				
	}

	public String getPropertyFile() throws InterruptedException {
		try
		{
			lock.tryLock(10, TimeUnit.SECONDS);
			return propertyFile;
		} 
		finally
		{
			lock.unlock();
		}
	}

	public void setPropertyFile(String propertyFile) throws InterruptedException {
		try
		{
			lock.tryLock(10, TimeUnit.SECONDS);
			this.propertyFile = propertyFile;
		} 
		finally
		{
			lock.unlock();
		}
	}
}