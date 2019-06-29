package com.revenat.myresume.presentation.image.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.revenat.myresume.infrastructure.util.CommonUtils;

/**
 * Container for temporary stored image before it would be uploaded.
 * 
 * @author Vitaliy Dragun
 *
 */
public class TemporaryImageStorage {
	private final Path largeImagePath;
	private final Path smallImagePath;

	public TemporaryImageStorage() throws IOException {
		this.largeImagePath = Files.createTempFile("large", ".jpg");
		this.smallImagePath = Files.createTempFile("small", ".jpg");
	}

	public Path getLargeImagePath() {
		return largeImagePath;
	}

	public Path getSmallImagePath() {
		return smallImagePath;
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}

}
