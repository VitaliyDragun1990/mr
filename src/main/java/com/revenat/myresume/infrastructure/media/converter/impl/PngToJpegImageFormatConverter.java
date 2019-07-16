package com.revenat.myresume.infrastructure.media.converter.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.revenat.myresume.infrastructure.media.converter.ImageFormatConverter;
import com.revenat.myresume.infrastructure.util.Checks;

@Component("pngToJpegImageFormatConverter")
class PngToJpegImageFormatConverter implements ImageFormatConverter {

	@Override
	public void convert(Path sourceImageFile, Path destImageFile) throws IOException {
		checkParams(sourceImageFile, destImageFile);
		
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(sourceImageFile.toFile());
			BufferedImage newBufferedImage =
					new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
			ImageIO.write(newBufferedImage, "jpg", destImageFile.toFile());
		} finally {
			if (bufferedImage != null) {
				bufferedImage.flush();
			}
		}

	}

	private static void checkParams(Path sourceImageFile, Path destImageFile) {
		Checks.checkParam(sourceImageFile != null, "sourceImageFile to convert image from can not be null");
		Checks.checkParam(destImageFile != null, "destImageFile to save converted image to can not be null");
	}

}
