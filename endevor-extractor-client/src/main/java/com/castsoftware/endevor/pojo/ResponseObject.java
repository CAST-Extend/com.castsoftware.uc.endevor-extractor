package com.castsoftware.endevor.pojo;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseObject<T> {
	public String returnCode;
	public String reasonCode;
	public Report reports;
	public T data;
	public List<String> messages;
	
	@Override
	public String toString() {
		return String.format("Return Code: %s\nReason Code: %s\nReports: %s\nMessages:\n%s", 
				returnCode, reasonCode, reports, messages.stream().collect(Collectors.joining("\n")));
	}
}
