package com.revenat.myresume.presentation.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents top-level error-handler component that intercepts all exception
 * can be thrown in the applicaiton and handles them appropriatelly.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ErrorHandlerFilter extends AbstractFilter {

	private boolean production;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);

		production = getEnv().getRequiredProperty("app.production", Boolean.class);
	}

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws ServletException, IOException {
		String requestUrl = req.getRequestURI();
		req.setAttribute("REQUEST_URL", requestUrl);
		try {
			chain.doFilter(req, resp);
		} catch (Throwable th) {
			LOGGER.error("Process request failed: " + requestUrl, th);
			handleException(th, requestUrl, resp);
		}
	}

	private void handleException(Throwable th, String requestUrl, HttpServletResponse resp)
			throws ServletException, IOException {
		if (production) {
			if (requestUrl.contains("/fragment") || "/error".equals(requestUrl)) {
				sendErrorStatus(resp);
			} else {
				resp.sendRedirect("/error");
			}
		} else {
			throw new ServletException(th);
		}
	}

	private void sendErrorStatus(HttpServletResponse resp) throws IOException {
		resp.reset();
		resp.getWriter().write("");
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
