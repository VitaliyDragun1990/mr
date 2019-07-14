package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Language;
import com.revenat.myresume.domain.document.LanguageLevel;
import com.revenat.myresume.domain.document.LanguageType;

@TypeConverter
class LanguageDTOToLanguageTransformer implements Converter<LanguageDTO, Language> {

	@Override
	public Language convert(LanguageDTO dto) {
		Language l = new Language();
		l.setName(dto.getName());
		l.setLevel(LanguageLevel.getLevel(dto.getLevel()));
		l.setType(LanguageType.getType(dto.getType()));
		return l;
	}

}
