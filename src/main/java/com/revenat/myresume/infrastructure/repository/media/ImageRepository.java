package com.revenat.myresume.infrastructure.repository.media;

import java.io.InputStream;

import com.revenat.myresume.infrastructure.exception.PersistenceException;

/**
 * Responsible for saving and deleting images
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageRepository {

	/**
	 * Saves photo image in original and small size using data obtained from provided {@link InputStream}
	 * 
	 * @param in input stream to get data from
	 * @param height height of the photo in pixels
	 * @param width width of the photo in pixels
	 * @return {@link ImagePair} which contains path to saved image or {@code null} if provided
	 *         input stream was {@code null}
	 * @throws PersistenceException
	 */
	ImagePair savePhoto(InputStream in);
	
	/**
	 * Saves certificate image in original and small size using data obtained from provided {@link InputStream}
	 * 
	 * @param in input stream to get data from
	 * @param height height of the photo in pixels
	 * @param width width of the photo in pixels
	 * @return {@link ImagePair} which contains path to saved image or {@code null} if provided
	 *         input stream was {@code null}
	 * @throws PersistenceException
	 */
	ImagePair saveCertificate(InputStream in);

	/**
	 * Deletes image if it exists in the first place
	 * @param photPath path to locate photo at
	 * @return {@code true} if photo has been deleted, {@code false} otherwise
	 */
	boolean deleteIfExists(String imagePath);
}
