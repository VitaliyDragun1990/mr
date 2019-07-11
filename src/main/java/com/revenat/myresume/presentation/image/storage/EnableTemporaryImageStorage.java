package com.revenat.myresume.presentation.image.storage;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

/**
 * Used on methods from which access to {@link TemporaryImageStorageManager} is
 * needed.
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
public @interface EnableTemporaryImageStorage {

}
