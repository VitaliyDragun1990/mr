package com.revenat.myresume.infrastructure.media.resizer;

import java.io.IOException;
import java.nio.file.Path;

import javax.annotation.Nonnull;

/**
 * Resizes images.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageResizer {

	/**
	 * Resizes specified {@code sourceImageFile} using provided {@code width} and
	 * {@code height} parameters and saves resized image as {@code destImageFile}
	 * 
	 * @param sourceImageFile path to image file to resize
	 * @param destImageFile   path to file resized image should be saved as
	 * @param width           with of the image to resize to
	 * @param height          height of the image to resize to
	 * @throws IOException
	 */
	void resizeImage(@Nonnull Path sourceImageFile, @Nonnull Path destImageFile, int width, int height)
			throws IOException;

}
