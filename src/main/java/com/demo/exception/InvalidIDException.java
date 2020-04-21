package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidIDException extends ServiceException {

	public InvalidIDException(String message) {
		super(message);
	}
	
}
