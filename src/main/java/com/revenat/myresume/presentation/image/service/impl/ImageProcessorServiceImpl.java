package com.revenat.myresume.presentation.image.service.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.revenat.myresume.infrastructure.media.converter.ImageFormatConverter;
import com.revenat.myresume.infrastructure.media.optimizer.ImageOptimizer;
import com.revenat.myresume.infrastructure.media.resizer.ImageResizer;
import com.revenat.myresume.infrastructure.util.Checks;
import com.revenat.myresume.presentation.image.exception.ImageProcessingException;
import com.revenat.myresume.presentation.image.model.ImageType;
import com.revenat.myresume.presentation.image.service.ImageProcessorService;
import com.revenat.myresume.presentation.image.storage.TemporaryImageStorage;

@Service
class ImageProcessorServiceImpl implements ImageProcessorService {

	private final ImageFormatConverter pngToJpegFormatConverter;
	private final ImageResizer imageResizer;
	private final ImageOptimizer imageOptimizer;

	@Autowired
	public ImageProcessorServiceImpl(
			@Qualifier("pngToJpegImageFormatConverter") ImageFormatConverter pngToJpegFormatConverter,
			ImageResizer imageResizer,
			ImageOptimizer imageOptimizer) {
		this.pngToJpegFormatConverter = pngToJpegFormatConverter;
		this.imageResizer = imageResizer;
		this.imageOptimizer = imageOptimizer;
	}

	@Override
	public void processImage(TemporaryImageStorage temporaryStorage, ImageType imageType, String contentType) {
		checkParams(temporaryStorage, imageType);
		
		Path sourcePath = temporaryStorage.getLargeImagePath();
		try {
			processConversion(contentType, sourcePath);
			processResizing(temporaryStorage, imageType, sourcePath);
			processOptimization(temporaryStorage);
		} catch (IOException e) {
			throw new ImageProcessingException("Can not process image: " + e.getMessage(), e);
		}

	}
	
	private void processConversion(String contentType, Path sourcePath) throws IOException {
		if (contentType.contains("png")) {
			pngToJpegFormatConverter.convert(sourcePath, sourcePath);
		} else if (!contentType.contains("jpg") && !contentType.contains("jpeg")) {
			throw new ImageProcessingException("Only png and jpg image formats are supported: Current content type=" + contentType);
		}
	}
	
	private void processResizing(TemporaryImageStorage temporaryStorage, ImageType imageType, Path sourcePath)
			throws IOException {
		imageResizer.resizeImage(sourcePath, temporaryStorage.getSmallImagePath(), imageType.getSmallWidth(),
				imageType.getSmallHeight());
		imageResizer.resizeImage(sourcePath, temporaryStorage.getLargeImagePath(), imageType.getLargeWidth(),
				imageType.getLargeHeight());
	}

	private void processOptimization(TemporaryImageStorage temporaryStorage) {
		imageOptimizer.optimize(temporaryStorage.getSmallImagePath());
		imageOptimizer.optimize(temporaryStorage.getLargeImagePath());
	}
	
	private static void checkParams(TemporaryImageStorage temporaryStorage, ImageType imageType) {
		Checks.checkParam(temporaryStorage != null, "temporaryStorage to process image can not be null");
		Checks.checkParam(imageType != null, "imageType of the image to process can not be null");
		Checks.checkParam(imageType != null, "contentType of the image to process can not be null");
	}

}
