package com.revenat.myresume.application.template;

import javax.annotation.Nonnull;

/**
 * Resolves content of some kind of template.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface TemplateContentResolver {

	@Nonnull String resolve(@Nonnull String template, @Nonnull Object model);
}
