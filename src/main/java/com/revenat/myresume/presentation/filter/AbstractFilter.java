package com.revenat.myresume.presentation.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractFilter implements Filter {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		doFilter(req, resp, chain);
	}
	
	protected abstract void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws ServletException, IOException;

	@Override
	public void destroy() {
		// do nothing
	}

}
