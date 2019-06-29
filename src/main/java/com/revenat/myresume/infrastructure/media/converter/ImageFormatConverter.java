package com.revenat.myresume.infrastructure.media.converter;

import java.io.IOException;
import java.nio.file.Path;

import javax.annotation.Nonnull;

/**
 * Converts image from one format to another.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageFormatConverter {

	void convertImage(@Nonnull Path sourceImageFile, @Nonnull Path destImagePath) throws IOException;
}
