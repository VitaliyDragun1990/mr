package com.revenat.myresume.application.transformer.impl;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.LanguageLevel;
import com.revenat.myresume.domain.entity.LanguageType;

@TypeConverter
class LanguageDTOToLanguageTransformer implements Converter<LanguageDTO, Language> {

	@Override
	public Language convert(LanguageDTO dto) {
		Language l = new Language();
		l.setId(dto.getId());
		l.setName(dto.getName());
		l.setLevel(LanguageLevel.getLevel(dto.getLevel()));
		l.setType(LanguageType.getType(dto.getType()));
		return l;
	}

}
