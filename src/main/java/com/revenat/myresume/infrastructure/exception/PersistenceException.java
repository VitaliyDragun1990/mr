package com.revenat.myresume.infrastructure.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signales about error with persistent-related activity.
 * 
 * @author Vitaliy Dragun
 *
 */
public class PersistenceException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public PersistenceException(Throwable cause) {
		super(cause);
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

}
