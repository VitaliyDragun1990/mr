package com.revenat.myresume.presentation.security.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals about error during sign up process.
 * 
 * @author Vitaliy Dragun
 *
 */
public class SignUpException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public SignUpException(Throwable cause) {
		super(cause);
	}

	public SignUpException(String message, Throwable cause) {
		super(message, cause);
	}

}
