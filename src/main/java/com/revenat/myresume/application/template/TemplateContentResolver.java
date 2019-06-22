package com.revenat.myresume.application.template;

/**
 * Resolves template content of some kind.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface TemplateContentResolver {

	String resolve(String template, Object model);
}
