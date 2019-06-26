package com.revenat.myresume.presentation.security;

import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.revenat.myresume.domain.entity.Profile;

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
	
	static AuthenticatedUser authenticate(Profile profile) {
		AuthenticatedUser authenticatedUser = new AuthenticatedUser(profile);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authenticatedUser, authenticatedUser.getPassword(), authenticatedUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authenticatedUser;
	}
	
	static void authenticate(AuthenticatedUser authUser) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authUser, authUser.getPassword(), authUser.getAuthorities());
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
