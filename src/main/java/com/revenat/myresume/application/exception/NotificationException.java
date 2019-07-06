package com.revenat.myresume.application.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals about error about notificaiton process.
 * 
 * @author Vitaliy Dragun
 *
 */
public class NotificationException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public NotificationException(String message) {
		super(message);
	}

	public NotificationException(String message, Throwable cause) {
		super(message, cause);
	}
}
