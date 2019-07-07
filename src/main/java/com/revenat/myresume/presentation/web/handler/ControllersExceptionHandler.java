package com.revenat.myresume.presentation.web.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.revenat.myresume.application.exception.ProfileNotFoundException;
import com.revenat.myresume.presentation.web.filter.ErrorHandlerFilter;

/**
 * This component is now working because of {@link ErrorHandlerFilter}, which
 * intercepts all exceptions before they can be processed by components
 * annotated with {@link ControllerAdvice} annotation
 * 
 * @author Vitaliy Dragun
 *
 */
@Component
class ControllersExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllersExceptionHandler.class);

	private final boolean production;

	@Autowired
	public ControllersExceptionHandler(@Value("${application.production}") String production) {
		this.production = Boolean.parseBoolean(production);
	}

//	@ExceptionHandler({ ProfileNotFoundException.class })
	public String handleProfileNotFoundException(ProfileNotFoundException e) {
		return "profile-not-found";
	}

	/* @ExceptionHandler({Exception.class}) */
	public ModelAndView handleGlobalException(Exception e, HttpServletRequest req) throws ServletException {
		String requestUrl = req.getRequestURI();
		ModelAndView model = new ModelAndView();
		LOGGER.error("Process request failed: {}. {}", requestUrl, e);
		return handleException(e, requestUrl, model);
	}

	private ModelAndView handleException(Exception e, String requestUrl, ModelAndView m) throws ServletException {
		if (production) {
			if ("/error".equals(requestUrl)) {
				throw new ServletException(e);
			} else {
				m.addObject("url", requestUrl);
				m.setViewName("error");
				return m;
			}
		} else {
			throw new ServletException(e);
		}
	}
}
