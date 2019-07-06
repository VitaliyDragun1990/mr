package com.revenat.myresume.presentation.security.service;

import javax.annotation.Nonnull;

import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

public interface SocialSignInService {

	@Nonnull String getAuthorizeUrl();
	
	@Nonnull AuthenticatedUser signIn(@Nonnull String verificationCode);
}
