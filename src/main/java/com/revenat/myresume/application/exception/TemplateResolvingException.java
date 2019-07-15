package com.revenat.myresume.application.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals about error during template resolving process.
 * 
 * @author Vitaliy Dragun
 *
 */
public class TemplateResolvingException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public TemplateResolvingException(String message, Throwable cause) {
		super(message, cause);
	}
}
