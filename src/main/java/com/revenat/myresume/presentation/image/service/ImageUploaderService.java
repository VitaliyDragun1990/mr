package com.revenat.myresume.presentation.image.service;

import javax.annotation.Nonnull;

import org.springframework.web.multipart.MultipartFile;

import com.revenat.myresume.presentation.image.exception.ImageUploadingException;
import com.revenat.myresume.presentation.image.model.UploadedCertificateResult;
import com.revenat.myresume.presentation.image.model.UploadedImageResult;

/**
 * Responsible for uploading user images
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageUploaderService {

	/**
	 * Uploads profile photo represented as {@link MultipartFile}
	 * 
	 * @return {@link UploadedImageResult} object containig links to uploaded image
	 * @throws ImageUploadingException
	 */
	@Nonnull
	UploadedImageResult uploadNewProfilePhoto(@Nonnull MultipartFile uploadedPhoto);

	/**
	 * Uploads certificate image represented as {@link MultipartFile}
	 * 
	 * @return {@link UploadedCertificateResult} object containig links to uploaded
	 *         certificate
	 * @throws ImageUploadingException
	 */
	@Nonnull
	UploadedCertificateResult uploadNewCertificateImage(@Nonnull MultipartFile uploadedCertificateImage);

	/**
	 * Clears temporary resources that may be created during uploading process.
	 * Optional operation. Implementations may provide empty realization or
	 * implement some clearing logic.
	 */
	void clearTemporaryResources();
}
