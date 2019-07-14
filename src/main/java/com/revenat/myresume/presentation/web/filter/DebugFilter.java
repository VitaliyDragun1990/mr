package com.revenat.myresume.presentation.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.myresume.infrastructure.util.DebugUtil;

public class DebugFilter extends AbstractFilter {

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws ServletException, IOException {
		try {
			LOGGER.info("******************************************************** start ***************************************");
			DebugUtil.turnOnShowMongoQuery();
			chain.doFilter(req, resp);
		} finally {
			DebugUtil.turnOffShowMongoQuery();
			LOGGER.info("******************************************************** end ***************************************");
		}
	}

}
