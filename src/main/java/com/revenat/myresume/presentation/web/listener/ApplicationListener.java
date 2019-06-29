package com.revenat.myresume.presentation.web.listener;

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
		
		setAttributes(container, env);	
		
		LOGGER.info("Aplication started");
	}

	private void setAttributes(ServletContext container, ConfigurableEnvironment env) {
		container.setAttribute("production", env.getRequiredProperty("app.production", Boolean.class));
		container.setAttribute("cssCommonVersion", env.getRequiredProperty("app.css.common.version", String.class));
		container.setAttribute("cssExVersion", env.getRequiredProperty("app.css.ex.version", String.class));
		container.setAttribute("jsCommonVersion", env.getRequiredProperty("app.js.common.version", String.class));
		container.setAttribute("jsExVersion", env.getRequiredProperty("app.js.ex.version", String.class));
		container.setAttribute("jsMessagesVersion", env.getRequiredProperty("app.js.messages.version", String.class));
		container.setAttribute("applicationHost", env.getRequiredProperty("app.host", String.class));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info("Application destroyed");
	}

}
