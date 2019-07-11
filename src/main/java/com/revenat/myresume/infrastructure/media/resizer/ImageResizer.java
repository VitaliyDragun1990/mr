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

	void resizeImage(@Nonnull Path sourceImageFile, @Nonnull Path destImageFile, int width, int height) throws IOException;
	
	void resizeImage(@Nonnull String sourceImageFile, @Nonnull String destImageFile, int width, int height) throws IOException;
}
