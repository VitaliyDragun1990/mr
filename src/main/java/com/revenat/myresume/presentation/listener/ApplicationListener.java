package com.revenat.myresume.presentation.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ApplicationListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext container = sce.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(container);
		ConfigurableEnvironment env = (ConfigurableEnvironment) context.getEnvironment();
		container.setAttribute("production", env.getRequiredProperty("application.production", Boolean.class));	
		
		LOGGER.info("Aplication started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info("Application destroyed");
	}

}
