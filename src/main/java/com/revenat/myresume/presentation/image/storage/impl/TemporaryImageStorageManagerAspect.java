package com.revenat.myresume.presentation.image.storage.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.revenat.myresume.presentation.image.exception.TemporaryImageStorageException;
import com.revenat.myresume.presentation.image.storage.TemporaryImageStorage;
import com.revenat.myresume.presentation.image.storage.TemporaryImageStorageManager;

@Aspect
@Component
class TemporaryImageStorageManagerAspect implements TemporaryImageStorageManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(TemporaryImageStorageManagerAspect.class);
	private final ThreadLocal<TemporaryImageStorage> currentTemporaryImageStorage = new ThreadLocal<>();

	@Override
	public TemporaryImageStorage getCurrentTemporaryImageStorage() {
		TemporaryImageStorage temporaryImageStorage = currentTemporaryImageStorage.get();
		if (temporaryImageStorage == null) {
			throw new TemporaryImageStorageException("Can not get current instance of TemporaryImageStorage."
					+ " Are you invoking this method inside one annotated with @EnableTemporaryImageStorage annotation?");
		}
		return temporaryImageStorage;
	}
	
	@Around("@annotation (com.revenat.myresume.presentation.image.annotation.EnableTemporaryImageStorage)")
	public Object manageCurrentTemporaryImageStorage(ProceedingJoinPoint joinPoint) throws Throwable {
		TemporaryImageStorage imageStorage = null;
		try {
			imageStorage = new TemporaryImageStorage();
			currentTemporaryImageStorage.set(imageStorage);
			LOGGER.debug("Before method: {}", joinPoint.getSignature());
			return joinPoint.proceed();
		} catch (IOException e) {
			throw new TemporaryImageStorageException("Can't create temp image files: " + e.getMessage(), e);
		} finally {
			LOGGER.debug("After method: {}", joinPoint.getSignature());
			currentTemporaryImageStorage.remove();
			if (imageStorage != null) {
				deleteQuietly(imageStorage.getLargeImagePath());
				deleteQuietly(imageStorage.getSmallImagePath());
			}
		}
	}

	private void deleteQuietly(Path path) {
		try {
			Files.deleteIfExists(path);
			LOGGER.debug("Temp file {} has been deleted successfully", path);
		} catch (IOException e) {
			LOGGER.debug("Can't delete temp file: " + path, e);
		}
		
	}

}
