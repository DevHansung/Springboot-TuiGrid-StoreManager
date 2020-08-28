package com.hansung.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DbCrudException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	private String message;

	public DbCrudException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}