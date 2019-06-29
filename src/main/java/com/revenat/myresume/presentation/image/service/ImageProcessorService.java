package com.revenat.myresume.presentation.image.service;

import javax.annotation.Nonnull;

import com.revenat.myresume.infrastructure.repository.media.ImageType;
import com.revenat.myresume.presentation.image.model.TemporaryImageStorage;

/**
 * Processes provided image using conversion, resizing and optimization
 * techniques.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageProcessorService {

	/**
	 * Processes specified image represented by {@link TemporaryImageStorage}
	 * argument according to provided {@link ImageType} and {@code contentType}.
	 * 
	 * @param temporaryStorage temporary storage for image to process
	 * @param imageType        type of the image to process
	 * @param contentType      content type of the image
	 * @throws {@link ImageProcessingException}
	 */
	void processImage(@Nonnull TemporaryImageStorage temporaryStorage, @Nonnull ImageType imageType, @Nonnull String contentType);
}
