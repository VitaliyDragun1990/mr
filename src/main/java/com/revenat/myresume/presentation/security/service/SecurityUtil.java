package com.revenat.myresume.presentation.security.service;

import java.util.List;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

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
	
	static void logout(FilterChainProxy securityFilterChain) {
		List<LogoutHandler> logoutHandlers = getLogoutHandlers(securityFilterChain);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		logout(logoutHandlers, auth, requestAttributes.getRequest(), requestAttributes.getResponse());
	}
	
	private static void logout(List<LogoutHandler> logoutHandlers, Authentication auth, HttpServletRequest request,
			HttpServletResponse response) {
		for (LogoutHandler logoutHandler : logoutHandlers) {
			logoutHandler.logout(request, response, auth);
		}
	}

	private static List<LogoutHandler> getLogoutHandlers(FilterChainProxy securityFilterChain) {
		LogoutFilter logoutFilter = findLogoutFilter(securityFilterChain);
		return ReflectionUtil.getFieldValue(logoutFilter, "handlers");
	}

	private static LogoutFilter findLogoutFilter(FilterChainProxy securityFilterChain) {
		List<Filter> filters = securityFilterChain.getFilters("/");
		for (Filter f : filters) {
			if (f instanceof LogoutFilter) {
				return (LogoutFilter) f;
			}
		}
		throw new IllegalStateException("Logout filter not found in filters: " + filters);
	}

	static Authentication authenticate(Profile profile) {
		AuthenticatedUser authenticatedUser = new AuthenticatedUser(profile);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authenticatedUser, authenticatedUser.getPassword(), authenticatedUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}
	
	static void authenticateWithRememberMe(Profile profile) {
		Authentication authentication = authenticate(profile);
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		WebApplicationContext ctx = 
				(WebApplicationContext) request.getServletContext()
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		CustomRememberMeService rememberMeService = ctx.getBean(CustomRememberMeService.class);
		rememberMeService.createAutoLoginToken(request, requestAttributes.getResponse(), authentication);
	}
	
	static Authentication authenticate(AuthenticatedUser authUser) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authUser, authUser.getPassword(), authUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}
	
	static AuthenticatedUser authenticateWithRememberMe(AuthenticatedUser authUser) {
		Authentication authentication = authenticate(authUser);
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		WebApplicationContext ctx = 
				(WebApplicationContext) request.getServletContext()
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		CustomRememberMeService rememberMeService = ctx.getBean(CustomRememberMeService.class);
		rememberMeService.createAutoLoginToken(request, requestAttributes.getResponse(), authentication);
		return authUser;
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
