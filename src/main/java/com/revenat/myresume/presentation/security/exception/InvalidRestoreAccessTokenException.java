package com.revenat.myresume.presentation.security.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Sigmals that provided restore access token is invalid.
 * 
 * @author Vitaliy Dragun
 *
 */
public class InvalidRestoreAccessTokenException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public InvalidRestoreAccessTokenException(String message) {
		super(message);
	}
}
