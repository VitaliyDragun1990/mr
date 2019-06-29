package com.revenat.myresume.presentation.image.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals about error condition in image processing process.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ImageProcessingException extends ApplicationException {
	private static final long serialVersionUID = 7639304085994175305L;

	public ImageProcessingException(String message) {
		super(message);
	}

	public ImageProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

}
