package com.revenat.myresume.domain.exception;

/**
 * Custom application specific general purpose exception.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

}
