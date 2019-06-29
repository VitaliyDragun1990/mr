package com.revenat.myresume.presentation.image.exception;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Signals about error during image uploading process
 * 
 * @author Vitaliy Dragun
 *
 */
public class ImageUploadingException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public ImageUploadingException(Throwable cause) {
		super(cause);
	}

	public ImageUploadingException(String msg, Exception e) {
		super(msg, e);
	}

}
