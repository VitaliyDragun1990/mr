package com.revenat.myresume.presentation.web.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.revenat.myresume.application.exception.ProfileNotFoundException;

// TODO: check if this component work because of ApplicationHandlerExceptionResolver is registered in WebConfig.
@ControllerAdvice
class ControllersExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllersExceptionHandler.class);
	
	private final boolean production;
	
	@Autowired
	public ControllersExceptionHandler(@Value("${application.production}") String production) {
		this.production = Boolean.parseBoolean(production);
	}

	@ExceptionHandler({ProfileNotFoundException.class})
	public String handleProfileNotFoundException(ProfileNotFoundException e, Model model) {
		return "profile-not-found";
	}
	
	/* @ExceptionHandler({Exception.class}) */
	public String handleGlobalException(Exception e, HttpServletRequest req, Model model) throws ServletException {
		String requestUrl = req.getRequestURI();
		LOGGER.error("Process request failed: {}. {}",requestUrl, e);
		return handleException(e, requestUrl, model);
	}
	
	private String handleException(Exception e, String requestUrl, Model m) throws ServletException {
		if (production) {
			if ("/error".equals(requestUrl)) {
				throw new ServletException(e);
			} else {
				m.addAttribute("url", requestUrl);
				return "error";
			}
		} else {
			throw new ServletException(e);
		}
	}
}
