package com.castsoftware.endevor;

import javax.ws.rs.core.Response;

import com.castsoftware.endevor.pojo.ResponseObject;

public class ResponseData<T> {
	private Response response;
	private ResponseObject<T> responseObject;
	
	public ResponseData(Response response, ResponseObject<T> responseObject) {
		super();
		this.response = response;
		this.responseObject = responseObject;
	}

	public Response getResponse() {
		return response;
	}

	public ResponseObject<T> getResponseObject() {
		return responseObject;
	}
}
