package com.revenat.myresume.application.exception;

public class CantCompleteClientRequestException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public CantCompleteClientRequestException(String message) {
		super(message);
	}

	public CantCompleteClientRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public CantCompleteClientRequestException(Throwable cause) {
		super(cause);
	}

}
