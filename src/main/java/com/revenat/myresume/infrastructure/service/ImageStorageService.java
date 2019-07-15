package com.revenat.myresume.infrastructure.service;

import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.revenat.myresume.presentation.image.model.ImageType;

/**
 * Responsible for storing and removing images.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageStorageService {

	/**
	 * Saves image from {@code tempImageFile} with specified {@code imageName}.
	 * 
	 * @param imageName     name of the image to save
	 * @param imageType     type of the image to save
	 * @param tempImageFile path to temporary file containing image to save
	 * @return link via which saved image can be accessed
	 */
	@Nonnull
	String save(@Nonnull String imageName, @Nonnull ImageType imageType, @Nonnull Path tempImageFile);

	/**
	 * Removes images associated with specified {@code imageLinks} if any
	 * 
	 * @param imageLinks links to images to delete
	 */
	void remove(@Nullable String... imageLinks);
}
