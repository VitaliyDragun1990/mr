package com.revenat.myresume.infrastructure.media.converter;

import javax.annotation.Nonnull;

/**
 * Converts non ASCII text into ASCII.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface TranslitConverter {

	String translit(@Nonnull String text);
}
