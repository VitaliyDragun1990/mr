package com.revenat.myresume.presentation.listener;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.SessionTrackingMode;
import javax.servlet.annotation.WebListener;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ApplicationListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext container = sce.getServletContext();
		container.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		
		Dynamic filterReg = container.addFilter("sitemesh", ConfigurableSiteMeshFilter.class);
		filterReg.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),
				true,
				"/*");
		
		
		LOGGER.info("Aplication started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info("Application destroyed");
	}

}
