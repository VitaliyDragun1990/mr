package com.revenat.myresume.application.exception;

import com.revenat.myresume.domain.entity.Profile;

/**
 * Signals that {@link Profile} with specified {@code uid} can't be found.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ProfileNotFoundException extends NotFoundException {
	private static final long serialVersionUID = 1L;

	public ProfileNotFoundException(String uid) {
		super(Profile.class, "uid", uid);
	}
}
