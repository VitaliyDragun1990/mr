package com.revenat.myresume.presentation.security.service;

import javax.annotation.Nonnull;

import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

/**
 * Manages sign up functionality
 * 
 * @author Vitaliy Dragun
 *
 */
public interface SignUpService {

	@Nonnull
	AuthenticatedUser signUp(@Nonnull String firstName, @Nonnull String lastName, @Nonnull String password);

	@Nonnull
	AuthenticatedUser signUp(@Nonnull Profile newProfile);
}
