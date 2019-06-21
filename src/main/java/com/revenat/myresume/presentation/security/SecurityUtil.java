package com.revenat.myresume.presentation.security;

import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.revenat.myresume.application.dto.ProfileDTO;

public class SecurityUtil {
	
	public static AuthenticatedUser getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof AuthenticatedUser) {
			return (AuthenticatedUser) principal;
		} else {
			return null;
		}
	}

	public static Long getAuthenticatedUserId() {
		AuthenticatedUser authenticatedUser = getAuthenticatedUser();
		return authenticatedUser != null ? authenticatedUser.getId() : null;
	}
	
	public static void authenticate(ProfileDTO profile) {
		AuthenticatedUser authenticatedUser = new AuthenticatedUser(profile);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authenticatedUser, authenticatedUser.getPassword(), authenticatedUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public static boolean isAuthenticated() {
		return getAuthenticatedUser() != null;
	}
	
	public static String generateNewActionUid() {
		return UUID.randomUUID().toString();
	}
	
	public static String generateNewRestoreAccessToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	private SecurityUtil() {}
}
