package com.revenat.myresume.infrastructure.media.converter.impl;

import org.springframework.stereotype.Component;

import com.revenat.myresume.infrastructure.media.converter.TranslitConverter;
import com.revenat.myresume.infrastructure.util.Checks;

import net.sf.junidecode.Junidecode;

@Component
class JunidecodeTranslitConverter implements TranslitConverter {

	@Override
	public String translit(String text) {
		Checks.checkParam(text != null, "text to convert to translit can not be null");
		return Junidecode.unidecode(text);
	}

}
