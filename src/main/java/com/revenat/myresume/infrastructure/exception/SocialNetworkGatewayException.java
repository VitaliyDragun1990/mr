package com.revenat.myresume.infrastructure.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Represents error during authetnication via some kind of social network.
 * 
 * @author Vitaliy Dragun
 *
 */
public class SocialNetworkGatewayException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public SocialNetworkGatewayException(String message, Throwable cause) {
		super(message, cause);
	}

}
