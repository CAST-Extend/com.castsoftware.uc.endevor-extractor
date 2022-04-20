package com.castsoftware.endevor.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.apache.log4j.Logger;

import com.castsoftware.endevor.ResponseData;
import com.castsoftware.endevor.RestUtil;
import com.castsoftware.endevor.TypeToExtension;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Worker.State;

//Extending Task would be enough if it was only a JavaFx app. 
//But since it can be be a java batch app, we have to cheat a bit
public class ExtractTask extends FutureTask<Boolean> {
	private static final Logger LOGGER = Logger.getLogger(ExtractTask.class);
	
	private String url;
	private String login;
	private String password;
	private String instance;
	private String env;
	private String stgNum;
	private String sys;
	private String sbs;
	private String type;
	private String ele;
	private String extractFolder;
	private ResponseData<List<String>> responseData;

	//Stolen from JavaFx Task
	private ObjectProperty<State> state = new SimpleObjectProperty<>(this, "state", State.READY);
	private final ObjectProperty<Boolean> value = new SimpleObjectProperty<>(this, "value");

	public ExtractTask(String url, String login, String password, String instance, String env, String stgNum,
			String sys, String sbs, String type, String ele, String extractFolder) 
	{
		//Stolen from JavaFx Task
		this(new TaskCallable());
		
		this.login = login;
		this.password = password;
		this.url = url;
		this.instance = instance;
		this.env = env;
		this.stgNum = stgNum;
		this.sys = sys;
		this.sbs = sbs;
		this.type = type;
		this.ele = ele;
		this.extractFolder = extractFolder;
	}
	
	//Stolen from JavaFx Task
	public ExtractTask(TaskCallable callableAdapter) {
		super(callableAdapter);
        callableAdapter.task = this;
	}

	public ExtractTask getClone() {
		return new ExtractTask(url, login, password, instance, env, stgNum, sys, sbs, type, ele, extractFolder);
	}

	protected Boolean call() throws Exception 
	{
		try
		{
	    	responseData = RestUtil.retrieveElement(login, password, url, instance, env, stgNum,
	    			sys, sbs, type, ele, false);

	    	if (responseData.getResponse() != null &&
	    			responseData.getResponse().getStatus() == 200 && 
	    			responseData.getResponseObject().data != null && 
	    			responseData.getResponseObject().data.size() > 0 &&
	    			!responseData.getResponseObject().data.get(0).isEmpty())
	    	{
	    		File file = new File(String.format("%s/%s/%s/%s/%s/%s/%s%s", 
	    				extractFolder, env, stgNum, sys, sbs, type, ele, TypeToExtension.getExtension(type)));
	    		file.getParentFile().mkdirs();
	    		
	    		try (Writer writer = new FileWriter(file)) 
	    		{
	    	        writer.write(responseData.getResponseObject().data.get(0));
	    	        if (LOGGER.isDebugEnabled())
	    	        	LOGGER.debug(String.format("Completed %s %s", type, ele));
	    	        return true;
	            }
	    	}
	    	else
	    	{
	    		if (responseData.getResponse() != null)
		    		LOGGER.warn(String.format("Element '%s' (Type: %s) got a return code %d (200 expected)\n%s", 
		    				ele, type, responseData.getResponse().getStatus(),
		    				responseData.getResponseObject().data.toString()));
	    		else
	    			LOGGER.error(String.format("Error getting element '%s' (Type: %s)", ele, type));
	    	}
		} catch (Exception e) {
			LOGGER.error(String.format("Extracting of element '%s' (Type: %s) failed", ele, type), e);
		} 
		return false;
	}
	
	public ResponseData<List<String>> getResponseData() {
		return responseData;
	}
	
	//Stolen from JavaFx Task
    public final void setState(State value) 
    {
        final State s = getState();
        if (s != State.CANCELLED) 
            this.state.set(value);
    }
    
    //Stolen from JavaFx Task
    public final State getState() { 
    	return state.get(); 
    }
    
    //Stolen from JavaFx Task
    public final Boolean getValue() {
    	return value.get();
    }
    
    //Stolen from JavaFx Task
    protected void updateValue(Boolean newValue) 
    {
    	value.set(newValue);
    }
	
    //Stolen from JavaFx Task
	private static final class TaskCallable implements Callable<Boolean> {
        private ExtractTask task;
        
        private TaskCallable() { }

        @Override public Boolean call() throws Exception {
        	Platform.runLater(() -> {
                task.setState(State.SCHEDULED);
                task.setState(State.RUNNING);
            });

            try {
                final Boolean result = task.call();
                if (!task.isCancelled()) {
                	Platform.runLater(() -> {
                        task.updateValue(result);
                        task.setState(State.SUCCEEDED);
                    });
                    return result;
                } else {
                    return null;
                }
            } catch (final Throwable th) {
            	Platform.runLater(() -> {
                    //task._setException(th);
                    task.setState(State.FAILED);
                });
                if (th instanceof Exception) {
                    throw (Exception) th;
                } else {
                    throw new Exception(th);
                }
            }
        }
    }
}
