package com.revenat.myresume.infrastructure.media.converter.impl;

import org.springframework.stereotype.Component;

import com.revenat.myresume.infrastructure.media.converter.TranslitConverter;

import net.sf.junidecode.Junidecode;

@Component
class JunidecodeTranslitConverter implements TranslitConverter {

	@Override
	public String translit(String text) {
		return Junidecode.unidecode(text);
	}

}
