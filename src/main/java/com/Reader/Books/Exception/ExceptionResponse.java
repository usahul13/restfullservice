package com.Reader.Books.Exception;

import java.util.Date;

public class ExceptionResponse {

	Date timestrap;
	String message;
	public ExceptionResponse(Date timestrap, String message, String details) {
		super();
		this.timestrap = timestrap;
		this.message = message;
		this.details = details;
	}
	String details;
	public Date getTimestrap() {
		return timestrap;
	}
	public void setTimestrap(Date timestrap) {
		this.timestrap = timestrap;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}
