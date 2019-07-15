package com.revenat.myresume.application.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals about exceptions during type transforming process.
 * 
 * @author Vitaliy Dragun
 *
 */
public class TypeTransformingException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public TypeTransformingException(String message, Throwable cause) {
		super(message, cause);
	}

}
