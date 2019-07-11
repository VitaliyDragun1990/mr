package com.revenat.myresume.presentation.image.exception;

import com.revenat.myresume.domain.exception.ApplicationException;
import com.revenat.myresume.presentation.image.storage.TemporaryImageStorage;

/**
 * Signals about error with {@link TemporaryImageStorage}
 * 
 * @author Vitaliy Dragun
 *
 */
public class TemporaryImageStorageException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public TemporaryImageStorageException(String message) {
		super(message);
	}

	public TemporaryImageStorageException(String message, Throwable cause) {
		super(message, cause);
	}

}
