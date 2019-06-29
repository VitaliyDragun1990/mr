package com.revenat.myresume.infrastructure.media.converter;

/**
 * Converts non ASCII text into ASCII.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface TranslitConverter {

	String translit(String text);
}
