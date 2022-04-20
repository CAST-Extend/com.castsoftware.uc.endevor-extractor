package com.castsoftware.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.Date;

public class FileUtil {
	//private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	private FileUtil()
	{
		
	}
	
	public static Date getModifiedAttribute(String fileName)
	{
		if ((fileName == null) || (fileName.isEmpty()))
			return null;
		Path p;
		p = Paths.get(fileName);
		try {
			return new Date(Files.readAttributes(p, BasicFileAttributes.class).lastModifiedTime().toMillis());
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String getFileChecksum(MessageDigest digest, File file) throws IOException
	{
	    //Get file input stream for reading the file content
	    FileInputStream fis = new FileInputStream(file);
	     
	    //Create byte array to read data in chunks
	    byte[] byteArray = new byte[1024];
	    int bytesCount = 0;
	      
	    //Read file data and update in message digest
	    while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };
	     
	    //close the stream; We don't need it now.
	    fis.close();
	     
	    //Get the hash's bytes
	    byte[] bytes = digest.digest();
	     
	    //This bytes[] has bytes in decimal format;
	    //Convert it to hexadecimal format
	    StringBuilder sb = new StringBuilder();
	    for(int i=0; i< bytes.length ;i++)
	    {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    //return complete hash
	   return sb.toString();
	}
	
	public static Boolean fileExists(String fileName)
	{
		if ((fileName == null) || (fileName.isEmpty()))
			return false;
		Path p;
		p = Paths.get(fileName);
		return Files.exists(p);
	}

    static public void exportResource(InputStream inputStream, String fileName) throws IOException {
        if(inputStream == null) 
        	return;

       OutputStream resStreamOut = null;
        try
        {
             int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(fileName);
            while ((readBytes = inputStream.read(buffer)) > 0) 
            {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } finally {
            resStreamOut.close();
        }
    }
    
    static public String getCurrentFolder() {
    	try {
			return new File(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getAbsolutePath();
		} catch (URISyntaxException e) {
			return "";
		}
    }
}
