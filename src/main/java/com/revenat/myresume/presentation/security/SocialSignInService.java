package com.revenat.myresume.presentation.security;

public interface SocialSignInService {

	String getAuthorizeUrl();
	
	AuthenticatedUser signIn(String verificationCode);
}
