package com.revenat.myresume.infrastructure.media.resizer.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revenat.myresume.infrastructure.media.resizer.ImageResizer;

import net.coobird.thumbnailator.Thumbnails;

@Component
class ThumbnailsImageResizer implements ImageResizer {
	private final String mediaDirParent;
	
	@Autowired
	public ThumbnailsImageResizer(@Value("${media.storage.root.path}") String mediaDirParent) {
		this.mediaDirParent = normalizeDirPath(mediaDirParent);
	}

	@Override
	public void resizeImage(Path sourceImageFile, Path destImageFile, int width, int height) throws IOException {
		Thumbnails.of(sourceImageFile.toFile()).size(width, height).toFile(destImageFile.toFile());
	}
	
	@Override
	public void resizeImage(String sourceImageFile, String destImageFile, int width, int height) throws IOException {
		Path sourceImgFile = Paths.get(mediaDirParent + sourceImageFile);
		Path destImgFile = Paths.get(mediaDirParent + destImageFile);
		resizeImage(sourceImgFile, destImgFile, width, height);
	}
	
	private String normalizeDirPath(String path) {
		String normalizedPath =  path.replace("\\", "/");
		if (normalizedPath.endsWith("/")) {
			normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
		}
		return normalizedPath;
	}

}
