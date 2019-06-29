package com.revenat.myresume.infrastructure.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

public class ImageStorageException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public ImageStorageException(Throwable cause) {
		super(cause);
	}

	public ImageStorageException(String message, Throwable cause) {
		super(message, cause);
	}

}
