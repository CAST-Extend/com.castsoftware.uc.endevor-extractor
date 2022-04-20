package com.castsoftware.endevor.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker.State;
import javafx.util.Duration;

public class Extractor {
	private static final Logger LOGGER = Logger.getLogger(Extractor.class);
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);
	private static ExecutorService retryExecutorService = Executors.newFixedThreadPool(1);
	
	private static Timeline monitorTaskList = new Timeline(new KeyFrame(
	        Duration.millis(1000),
	        ae -> taskListUpdate()));	
	
	private static Boolean isSecondTry;
	private static long startTime;	
	
	private static List<ExtractTask> taskList = new ArrayList<ExtractTask>();
	
	private static void taskListUpdate()
	{
		if (taskList.stream().filter(task -> task.getState().equals(State.READY) ||
				task.getState().equals(State.RUNNING) || task.getState().equals(State.SCHEDULED)).count() == 0)
		{
			monitorTaskList.stop();
			LOGGER.info(String.format("Duration: %.3f s", (double)(System.nanoTime() - startTime) / 1000000000));
			LOGGER.info(String.format("Success: %d", taskList.stream().filter(t -> t.getState().equals(State.SUCCEEDED)).count()));
			LOGGER.info(String.format("Failed: %d", taskList.stream().filter(t -> t.getState().equals(State.FAILED)).count()));
			LOGGER.info(String.format("Cancelled: %d", taskList.stream().filter(t -> t.getState().equals(State.CANCELLED)).count()));
			LOGGER.info(String.format("Extracted: %d", taskList.stream().filter(t -> t.getValue()).count()));
			LOGGER.info(String.format("Not Extracted: %d", taskList.stream().filter(t -> !t.getValue()).count()));
			
			if (!isSecondTry)
			{
				taskList.removeIf(t -> t.getValue()); 
				//TODO retry only 500? based on CA codes?
				taskList.removeIf(t -> t.getResponseData() != null && t.getResponseData().getResponse().getStatus() != 500);
				
				if (taskList.size() > 0)
				{
					LOGGER.info(String.format("Trying the %d non-extracted once more...", taskList.size()));
					isSecondTry = true;
					//Clone Tasks (they can only be used once)
					taskList.addAll(taskList.stream().map(t -> t.getClone()).collect(Collectors.toList()));
					//Remove originals
					taskList.removeIf(t -> !t.getState().equals(State.READY));
					//Submit again on single-thread executor
					taskList.forEach(t -> retryExecutorService.submit(t));
					monitorTaskList.play();	
				}
			}
			else
			{
				taskList.clear();
			}
		}
	}
	
	public static void start()
	{
		startTime = System.nanoTime();
    	isSecondTry = false;
    	monitorTaskList.play();
	}
	
	public static Boolean isRunning() {
		return taskList.stream().filter(task -> task.getState().equals(State.READY) ||
				task.getState().equals(State.RUNNING) || task.getState().equals(State.SCHEDULED)).count() > 0;
	}
	
	public static double getProgress() 
	{
		double size = 1.0 * taskList.size();
		long notCompleted = taskList.stream().filter(task -> task.getState().equals(State.READY) ||
				task.getState().equals(State.RUNNING) || task.getState().equals(State.SCHEDULED)).count();

		if (size > 0)
			return (size - notCompleted) / size;
		else
			return 1;
	}
	
	public static long getRemaining() {
		return taskList.stream().filter(task -> task.getState().equals(State.READY) ||
				task.getState().equals(State.RUNNING) || task.getState().equals(State.SCHEDULED)).count();
	}
	
	public static Boolean isShutdown() {
		return Extractor.executorService.isTerminated() && Extractor.retryExecutorService.isTerminated();
	}
	
	public static void shutdown()
	{
		Extractor.executorService.shutdown();
    	Extractor.retryExecutorService.shutdown();
	}
	
	public static void shutdownNow() 
	{
		Extractor.executorService.shutdownNow();
		Extractor.retryExecutorService.shutdownNow();
		do 
    	{
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				LOGGER.error("Sleep interrupted while waiting for executors to shutdown", e);
			}
    	} while (!Extractor.executorService.isTerminated() && !Extractor.retryExecutorService.isTerminated());	
	}
	
	public static void newExtractTask(String url, String login, String password, String instance, String env, String stgNum,
			String sys, String sbs, String type, String ele, String extractFolder)
	{
		ExtractTask task = new ExtractTask(url, login, password, instance, env, stgNum, sys, sbs, type, ele, extractFolder);
    	taskList.add(task);
    	executorService.submit(task);
	}
	
	static {
		monitorTaskList.setCycleCount(Timeline.INDEFINITE);
	}
}
