package com.revenat.myresume.presentation.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomRememberMeService extends PersistentTokenBasedRememberMeServices {

	@Autowired
	public CustomRememberMeService(UserDetailsService userDetailsService,PersistentTokenRepository tokenRepository) {
		super("my-resume-online", userDetailsService, tokenRepository);
	}

	void createAutoLoginToken(HttpServletRequest request, HttpServletResponse response, Authentication successAuthentication) {
		super.onLoginSuccess(request, response, successAuthentication);
	}
}
