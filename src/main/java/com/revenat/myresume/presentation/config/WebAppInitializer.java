package com.revenat.myresume.presentation.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.revenat.myresume.application.config.ServiceConfig;
import com.revenat.myresume.infrastructure.config.ElasticSearchConfig;
import com.revenat.myresume.infrastructure.config.GatewayConfig;
import com.revenat.myresume.infrastructure.config.JPAConfig;
import com.revenat.myresume.infrastructure.config.MediaConfig;
import com.revenat.myresume.presentation.web.filter.ErrorHandlerFilter;
import com.revenat.myresume.presentation.web.listener.ApplicationListener;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		super.onStartup(container);
		registerFilters(container);
		container.addListener(ApplicationListener.class);
	}

	private void registerFilters(ServletContext container) {
		registerFilter(container, buildConfigurableSiteMeshfiler(), "sitemesh");
	}
	
	private void registerFilter(ServletContext container, Filter filter, String... filterNames) {
		String filterName = filterNames.length > 0 ? filterNames[0] : filter.getClass().getSimpleName();
		container.addFilter(filterName, filter).addMappingForUrlPatterns(
				null,
				true, 
				"/*");
	}

	private ConfigurableSiteMeshFilter buildConfigurableSiteMeshfiler() {
		return new ConfigurableSiteMeshFilter() {
			@Override
			protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
				builder
					.addDecoratorPath("/*", "/WEB-INF/template/page-template.jsp")
					.addDecoratorPath("/fragment/*", "/WEB-INF/template/fragment-template.jsp")
					.addDecoratorPath("/welcome/fragment/*", "/WEB-INF/template/fragment-template.jsp")
					.addDecoratorPath("/search/fragment/*", "/WEB-INF/template/fragment-template.jsp");
			}
		};
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {
			WebSecurityConfig.class,
			ServiceConfig.class,
			JPAConfig.class,
			ElasticSearchConfig.class,
			GatewayConfig.class,
			MediaConfig.class
			};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {
				new RequestContextFilter(),
				new ErrorHandlerFilter(),
				new CharacterEncodingFilter("UTF-8", true),
				new OpenEntityManagerInViewFilter()
				};
	}

}
