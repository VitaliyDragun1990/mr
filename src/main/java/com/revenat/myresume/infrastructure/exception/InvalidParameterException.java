package com.revenat.myresume.infrastructure.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals that object preconditions have been violated in form of invalid
 * method parameter value.
 * 
 * @author Vitaliy Dragun
 *
 */
public class InvalidParameterException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public InvalidParameterException(String message) {
		super(message);
	}

}
