package com.revenat.myresume.infrastructure.media.resizer.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import com.revenat.myresume.infrastructure.media.resizer.ImageResizer;
import com.revenat.myresume.infrastructure.util.Checks;

import net.coobird.thumbnailator.Thumbnails;

@Component
class ThumbnailsImageResizer implements ImageResizer {

	@Override
	public void resizeImage(Path sourceImageFile, Path destImageFile, int width, int height) throws IOException {
		checkParams(sourceImageFile, destImageFile, width, height);
		
		Thumbnails.of(sourceImageFile.toFile()).size(width, height).toFile(destImageFile.toFile());
	}

	private static void checkParams(Path sourceImageFile, Path destImageFile, int width, int height) {
		Checks.checkParam(sourceImageFile != null, "sourceImageFile file for image to resize can not be null");
		Checks.checkParam(destImageFile != null, "destImageFile to store resized image to can not be null");
		Checks.checkParam(width > 0, "witch used to resize image with can not be negative value: " + width);
		Checks.checkParam(height > 0, "height used to resize image with can not be negative value: " + height);
	}

}
