package com.revenat.myresume.infrastructure.gateway.filesystem;

import java.io.InputStream;

import com.revenat.myresume.infrastructure.exception.FileSystemGatewayException;

/**
 * Represents gateway to underlying filesystem
 * 
 * @author Vitaliy Dragun
 *
 */
public interface FileSystemGateway {

	/**
	 * Creates and saves new file with content from specified {@code in} input
	 * stream.
	 * 
	 * @param in       {@link InputStream} to get file content from
	 * @param fileName name of the file to be created
	 * @return full path to saved file
	 * @throws FileSystemGatewayException
	 */
	String saveFile(InputStream in, String fileName);

	/**
	 * Deletes file with given name if exists.
	 * 
	 * @param fileName name of the file to delete
	 * @return {@code true} if file has been deleted, {@code false} otherwise
	 */
	boolean deleteFileIfExists(String fileName);
}
