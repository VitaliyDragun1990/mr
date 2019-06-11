package com.revenat.myresume.presentation.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionTrackingMode;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.revenat.myresume.presentation.controller.ProfileController;
import com.revenat.myresume.presentation.filter.ApplicationFilter;
import com.revenat.myresume.presentation.listener.ApplicationListener;

public class ResumeWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		WebApplicationContext ctx = createWebApplicationContext(container);

		container.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		container.addListener(new ContextLoaderListener(ctx));
		container.addListener(ctx.getBean(ApplicationListener.class));
		
		registerFilters(container, ctx);
		registerServlet(container, ctx.getBean(ProfileController.class), "/profile");
	}
	
	private WebApplicationContext createWebApplicationContext(ServletContext container) {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.scan(
				"com.revenat.myresume.presentation.config",
				"com.revenat.myresume.application.config");
		ctx.setServletContext(container);
		ctx.refresh();
		return ctx;
	}

	private void registerServlet(ServletContext container, Servlet servletInstance, String url) {
		ServletRegistration.Dynamic servletReg =
				container.addServlet(servletInstance.getClass().getSimpleName(), servletInstance);
		servletReg.setLoadOnStartup(1);
		servletReg.addMapping(url);
		
	}

	private void registerFilters(ServletContext container, WebApplicationContext ctx) {
		registerFilter(container, new CharacterEncodingFilter("UTF-8", true));
		registerFilter(container, ctx.getBean(ApplicationFilter.class));
		registerFilter(container, buildConfigurableSiteMeshfiler(), "sitemesh");
		
	}
	
	private void registerFilter(ServletContext container, Filter filter, String... filterNames) {
		String filterName = filterNames.length > 0 ? filterNames[0] : filter.getClass().getSimpleName();
		container.addFilter(filterName, filter).addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),
				true, 
				"/*");
		
	}

	private ConfigurableSiteMeshFilter buildConfigurableSiteMeshfiler() {
		return new ConfigurableSiteMeshFilter() {
			@Override
			protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
				builder.addDecoratorPath("/*", "/WEB-INF/template/page-template.jsp");
			}
		};
	}
}
