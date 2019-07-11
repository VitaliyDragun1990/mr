package com.revenat.myresume.presentation.security.service;

import javax.annotation.Nonnull;

import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

/**
 * Allows to authenticate users via their social network accounts.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface SocialSignInService {

	@Nonnull
	String getAuthorizeUrl();

	@Nonnull
	AuthenticatedUser signIn(@Nonnull String verificationCode);
}
