package com.revenat.myresume.presentation.security.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals about error during social sign in process.
 * 
 * @author Vitaliy Dragun
 *
 */
public class SocialSignInException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public SocialSignInException(Throwable cause) {
		super(cause);
	}

	public SocialSignInException(String message, Throwable cause) {
		super(message, cause);
	}

}
