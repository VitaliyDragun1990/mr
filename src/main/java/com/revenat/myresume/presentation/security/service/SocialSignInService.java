package com.revenat.myresume.presentation.security.service;

import javax.annotation.Nonnull;

import com.revenat.myresume.presentation.security.exception.SocialSignInException;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

/**
 * Allows to authenticate users via their social network accounts.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface SocialSignInService {

	/**
	 * Returns special authorize url user should follow to authenticate themselves
	 * via their social network account.
	 */
	@Nonnull
	String getAuthorizeUrl();

	/**
	 * Processes sign in request using special {@code verificationCode}
	 * 
	 * @param verificationCode special verification token to sign in particular user
	 *                         via their social network account
	 * @return instance of authenticated user in the application
	 * @throws SocialSignInException if some error occurs during sign in process
	 */
	@Nonnull
	AuthenticatedUser signIn(@Nonnull String verificationCode);
}
