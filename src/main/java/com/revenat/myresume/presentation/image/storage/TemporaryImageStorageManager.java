package com.revenat.myresume.presentation.image.storage;

import javax.annotation.Nonnull;

import com.revenat.myresume.presentation.image.exception.TemporaryImageStorageException;

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
	@Nonnull TemporaryImageStorage getCurrentTemporaryImageStorage();
}
