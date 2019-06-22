package com.revenat.myresume.infrastructure.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

public class EmailGatewayException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public EmailGatewayException(Throwable cause) {
		super(cause);
	}

}
