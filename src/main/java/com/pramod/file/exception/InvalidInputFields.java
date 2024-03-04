package com.pramod.file.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidInputFields extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputFields() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidInputFields(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleInvalidInputFields(InvalidInputFields e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

}
