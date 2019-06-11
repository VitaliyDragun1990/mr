package com.revenat.myresume.presentation.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.revenat.myresume.application.service.NameService;

@Controller
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 5148422268824263450L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
	
	private final NameService nameService;
	
	@Autowired
	public ProfileController(NameService nameService) {
		this.nameService = nameService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.debug("doGet profile");
		String param = req.getParameter("name");
		if (StringUtils.isNotBlank(param)) {
			req.setAttribute("name", nameService.convertName(param));
		}
		req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
	}
}
