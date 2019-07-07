package com.revenat.myresume.presentation.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.revenat.myresume.presentation.web.filter.ErrorHandlerFilter;

/**
 * This component responsible for rethrowing all unhandled exceptions for
 * top-level handling, which is done by {@link ErrorHandlerFilter} component.
 * 
 * @author Vitaliy Dragun
 *
 */
@Component("globalHandlerExceptionResolver")
class GlobalHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {
	private int order = Ordered.LOWEST_PRECEDENCE;

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		} else {
			throw new IllegalArgumentException(ex);
		}
	}

}
