package com.revenat.myresume.application.template.impl;

import java.io.IOException;
import java.io.StringReader;

import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.revenat.myresume.application.template.TemplateContentResolver;
import com.revenat.myresume.domain.exception.ApplicationException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Default implementation of the {@link TemplateContentResolver} which uses
 * {@code Freemarker} template engine to resolve given template.
 * 
 * @author Vitaliy Dragun
 *
 */
@Component
class FreemarkerTemplateContentResolver implements TemplateContentResolver {

	@Override
	public String resolve(String stringTemplate, Object model) {
		try {
			Template template = new Template("",
					new StringReader(stringTemplate),
					new Configuration(Configuration.VERSION_2_3_28));
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException | TemplateException e) {
			throw new ApplicationException("Can't resolve freemarker string template: " + e.getMessage(), e);
		}
	}

}
