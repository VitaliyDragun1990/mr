package com.revenat.myresume.application.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals about error in data generation process.
 * 
 * @author Vitaliy Dragun
 *
 */
public class DataGenerationException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public DataGenerationException(String message) {
		super(message);
	}

}
