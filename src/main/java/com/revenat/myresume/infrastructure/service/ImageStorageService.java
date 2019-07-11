package com.revenat.myresume.infrastructure.service;

import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.revenat.myresume.presentation.image.model.ImageType;

/**
 * Responsible for saving and removing images.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageStorageService {

	@Nonnull
	String saveAndReturnImageLink(@Nonnull String imageName, @Nonnull ImageType imageType, @Nonnull Path tempImageFile);

	void remove(@Nullable String... imageLinks);
}
