package com.revenat.myresume.infrastructure.exception;

import com.revenat.myresume.domain.exception.ApplicationException;
import com.revenat.myresume.infrastructure.gateway.filesystem.FileSystemGateway;

/**
 * Represents error while working with file system via {@link FileSystemGateway}
 * 
 * @author Vitaliy Dragun
 *
 */
public class FileSystemGatewayException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public FileSystemGatewayException(String message, Throwable cause) {
		super(message, cause);
	}

}
