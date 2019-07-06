package com.revenat.myresume.presentation.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Handles {@link AccessDeniedException}
 * 
 * @author Vitaliy Dragun
 *
 */
@Component("applicationAccessDeniedHandler")
class ApplicationAccessDeniedHandler implements AccessDeniedHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAccessDeniedHandler.class);

	private final boolean production;

	@Autowired
	public ApplicationAccessDeniedHandler(@Value("${app.production}") boolean production) {
		this.production = production;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		LOGGER.warn("Detected AccessDeniedexception: " + accessDeniedException.getMessage(), accessDeniedException);
		if (production) {
			response.sendRedirect("/error?status=403");
		} else {
			throw new ServletException(accessDeniedException);
		}
	}

}
