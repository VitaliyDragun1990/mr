package com.revenat.myresume.presentation.image.service;

import com.revenat.myresume.presentation.image.model.TemporaryImageStorage;

/**
 * Manages {@link TemporaryImageStorage} objects
 * 
 * @author Vitaliy Dragun
 *
 */
public interface TemporaryImageStorageManager {

	TemporaryImageStorage getCurrentTemporaryImageStorage();
}
