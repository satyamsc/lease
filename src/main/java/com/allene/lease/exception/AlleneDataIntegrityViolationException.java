package com.allene.lease.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class AlleneDataIntegrityViolationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AlleneDataIntegrityViolationException(String message) {
		super(message);
	}
}