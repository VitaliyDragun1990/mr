package com.revenat.myresume.presentation.config;

import java.util.EnumSet;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.revenat.myresume.application.config.ApplicationConfig;
import com.revenat.myresume.infrastructure.config.InfrastructureConfig;
import com.revenat.myresume.presentation.web.filter.DebugFilter;
import com.revenat.myresume.presentation.web.filter.ErrorHandlerFilter;
import com.revenat.myresume.presentation.web.listener.ApplicationListener;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	private AnnotationConfigWebApplicationContext rootWebAppContext;
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		super.onStartup(container);
		container.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		registerFilters(container);
		container.addListener(ApplicationListener.class);
	}

	private void registerFilters(ServletContext container) {
		registerFilter(container, buildConfigurableSiteMeshfiler(), "sitemesh");
		registerDebugFilterIfEnabled(container, new DebugFilter());
	}
	
	private void registerDebugFilterIfEnabled(ServletContext container, DebugFilter filter) {
		ConfigurableEnvironment env = rootWebAppContext.getEnvironment();
		
		boolean isDebugEnabled = env.getRequiredProperty("app.debug.enabled", Boolean.class);
		String[] debugUrl = env.getRequiredProperty("app.debug.url", String[].class);
		
		if (isDebugEnabled && debugUrl.length != 0) {
			Dynamic filterRegistration = container.addFilter(filter.getClass().getSimpleName(), filter);
			for (String url : debugUrl) {
				filterRegistration.addMappingForUrlPatterns(null, true, url);
			}
		}
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
					.addDecoratorPath("/search/fragment/*", "/WEB-INF/template/fragment-template.jsp")
					.addTagRuleBundle(new Sm2TagRuleBundle());
			}
		};
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {
			ImageProcessingConfig.class,
			WebSecurityConfig.class,
			ApplicationConfig.class,
			InfrastructureConfig.class
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
				new ErrorHandlerFilter(),
				new CharacterEncodingFilter("UTF-8", true),
				//new OpenEntityManagerInViewFilter(), // TODO: remove this filter
				new RequestContextFilter()
				};
	}
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		this.rootWebAppContext = (AnnotationConfigWebApplicationContext) super.createRootApplicationContext();
		rootWebAppContext.refresh();
		return rootWebAppContext;
	}

}
