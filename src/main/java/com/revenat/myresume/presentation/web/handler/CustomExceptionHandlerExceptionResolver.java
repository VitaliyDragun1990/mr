package com.revenat.myresume.presentation.web.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * Custom extending of {@link ExceptionHandlerExceptionResolver} with changed
 * order precedence to make it run before
 * {@link GlobalHandlerExceptionResolver} exception resolver. In particular
 * this component responsible to passig exceptions thrown by controllers to
 * method annotated with {@link ExceptionHandler} annotain.
 * 
 * @author Vitaliy Dragun
 *
 */
@Component("customExceptionHandlerExceptionResolver")
class CustomExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

	public CustomExceptionHandlerExceptionResolver() {
		setOrder(LOWEST_PRECEDENCE - 1);
	}
}
