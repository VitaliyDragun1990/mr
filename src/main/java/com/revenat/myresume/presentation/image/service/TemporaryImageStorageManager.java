package com.revenat.myresume.presentation.image.service;

import com.revenat.myresume.presentation.image.annotation.EnableTemporaryImageStorage;
import com.revenat.myresume.presentation.image.exception.TemporaryImageStorageException;
import com.revenat.myresume.presentation.image.model.TemporaryImageStorage;

/**
 * Manages {@link TemporaryImageStorage} objects
 * 
 * @author Vitaliy Dragun
 *
 */
public interface TemporaryImageStorageManager {

	/**
	 * Returns current {@link TemporaryImageStorage} instance. Must be invoken
	 * inside methods annotated with {@link EnableTemporaryImageStorage} annotation.
	 * 
	 * @return current instance of the {@link EnableTemporaryImageStorage}
	 * @throws {@link TemporaryImageStorageException} if current temporary storage
	 *                can not be obtained for some reason
	 */
	TemporaryImageStorage getCurrentTemporaryImageStorage();
}
