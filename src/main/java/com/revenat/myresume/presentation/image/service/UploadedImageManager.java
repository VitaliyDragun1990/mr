package com.revenat.myresume.presentation.image.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.util.Checks;
import com.revenat.myresume.presentation.image.model.UploadedImageResult;

/**
 * Manages image links uploaded by user during his session. If user's session ends
 * and some image links are still left mnanaged by this component, that means
 * user did not save them and they must be removed. After user saved uploaded
 * images he must explicitelly call {@link #clearImageLinks()} to remove links
 * to those images, otherwise all image links that left managed by this component
 * will be removed after user's session ends.
 * 
 * @author Vitaliy Dragun
 *
 */
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UploadedImageManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadedImageManager.class);

	private transient ImageStorageService imageService;
	private List<UploadedImageResult> imageLinks;

	public UploadedImageManager(ImageStorageService imageService) {
		this.imageService = imageService;
		imageLinks = new ArrayList<>(6);
	}

	protected List<UploadedImageResult> getImageLinks() {
		if (imageLinks == null) {
			imageLinks = new ArrayList<>(6);
		}
		return imageLinks;
	}

	public void addImageLinks(@Nonnull UploadedImageResult imageLinks) {
		Checks.checkParam(imageLinks != null, "imageLinks to add can not be null");
		
		getImageLinks().add(imageLinks);
	}

	public void clearImageLinks() {
		getImageLinks().clear();
	}

	@PreDestroy
	private void removeImageLinks() {
		if (!getImageLinks().isEmpty()) {
			for (UploadedImageResult image : imageLinks) {
				imageService.remove(image.getLargeImageLink());
				imageService.remove(image.getSmallImageLink());
			}
			LOGGER.info("Removed {} temporary images", imageLinks);
		}
	}

}
