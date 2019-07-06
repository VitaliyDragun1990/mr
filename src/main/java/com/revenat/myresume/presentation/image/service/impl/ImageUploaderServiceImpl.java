package com.revenat.myresume.presentation.image.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.domain.exception.ApplicationException;
import com.revenat.myresume.infrastructure.repository.media.ImageType;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.presentation.image.annotation.EnableTemporaryImageStorage;
import com.revenat.myresume.presentation.image.exception.ImageUploadingException;
import com.revenat.myresume.presentation.image.model.TemporaryImageStorage;
import com.revenat.myresume.presentation.image.model.UploadedCertificateResult;
import com.revenat.myresume.presentation.image.model.UploadedImageResult;
import com.revenat.myresume.presentation.image.service.ImageProcessorService;
import com.revenat.myresume.presentation.image.service.ImageUploaderService;
import com.revenat.myresume.presentation.image.service.TemporaryImageStorageManager;
import com.revenat.myresume.presentation.image.service.UploadedImageManager;

@Service
class ImageUploaderServiceImpl implements ImageUploaderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageUploaderServiceImpl.class);

	private final ImageProcessorService imageProcessoService;
	private final ImageStorageService imageStorageService;
	private final TemporaryImageStorageManager temporaryImageStorageManager;
	private final UploadedImageManager uploadedImageManager;
	private final DataGenerator dataGenerator;

	@Autowired
	public ImageUploaderServiceImpl(ImageProcessorService imageProcessoService, ImageStorageService imageStorageService,
			TemporaryImageStorageManager temporaryImageStorageManager, UploadedImageManager uploadedImageManager,
			DataGenerator dataGenerator) {
		this.imageProcessoService = imageProcessoService;
		this.imageStorageService = imageStorageService;
		this.temporaryImageStorageManager = temporaryImageStorageManager;
		this.uploadedImageManager = uploadedImageManager;
		this.dataGenerator = dataGenerator;
	}

	@Override
	@EnableTemporaryImageStorage
	public UploadedImageResult uploadNewProfilePhoto(MultipartFile uploadedPhoto) {
		try {
			return processUpload(uploadedPhoto, ImageType.AVATARS);
		} catch (IOException | ApplicationException e) {
			throw new ImageUploadingException("Can't save uploaded profile photo: " + e.getMessage(), e);
		}
	}

	@Override
	@EnableTemporaryImageStorage
	public UploadedCertificateResult uploadNewCertificateImage(MultipartFile uploadedCertificateImage) {
		try {
			UploadedImageResult uploadedImage = processUpload(uploadedCertificateImage, ImageType.CERTIFICATES);
			uploadedImageManager.addImageLinks(uploadedImage);
			String certificateName = dataGenerator
					.generateCertificateName(uploadedCertificateImage.getOriginalFilename());
			return new UploadedCertificateResult(certificateName, uploadedImage.getLargeUrl(),
					uploadedImage.getSmallUrl());
		} catch (IOException | ApplicationException e) {
			throw new ImageUploadingException("Can't save uploaded sertificate image: " + e.getMessage(), e);
		}
	}

	/**
	 * Release uploaded certificate image links from being removed automatically when
	 * user session ends.
	 */
	@Override
	public void clearTemporaryResources() {
		uploadedImageManager.clearImageLinks();
	}

	private UploadedImageResult processUpload(MultipartFile upload, ImageType imageType) throws IOException {
		TemporaryImageStorage temporaryImageStorage = getCurrentTemporaryImageStorage();
		transferUploadToTemporaryImageStorage(upload, temporaryImageStorage);
		imageProcessoService.processImage(temporaryImageStorage, imageType, upload.getContentType());

		return saveUploadedImage(imageType, temporaryImageStorage);
	}

	private TemporaryImageStorage getCurrentTemporaryImageStorage() {
		return temporaryImageStorageManager.getCurrentTemporaryImageStorage();
	}

	private void transferUploadToTemporaryImageStorage(MultipartFile upload,
			TemporaryImageStorage temporaryImageStorage) throws IOException {
		LOGGER.debug("Transfering upload with content type: {}", upload.getContentType());
		upload.transferTo(temporaryImageStorage.getLargeImagePath().toFile());
	}

	private UploadedImageResult saveUploadedImage(ImageType imageType, TemporaryImageStorage temporaryImageStorage) {
		String largeImageName = generateNewImageName();
		String smallImageName = generateSmallImageName(largeImageName);
		String largeImageLink = imageStorageService.saveAndReturnImageLink(largeImageName, imageType,
				temporaryImageStorage.getLargeImagePath());
		String smallImageLink = imageStorageService.saveAndReturnImageLink(smallImageName, imageType,
				temporaryImageStorage.getSmallImagePath());
		return new UploadedImageResult(largeImageLink, smallImageLink);
	}

	private String generateNewImageName() {
		return UUID.randomUUID().toString() + ".jpg";
	}

	private String generateSmallImageName(String largeImageName) {
		return largeImageName.replace(".jpg", "-sm.jpg");
	}

}
