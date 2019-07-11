package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.domain.entity.Language;

@TypeConverter
class LanguageDTOsToLanguagesTransformer implements Converter<List<LanguageDTO>, List<Language>> {
	private LanguageDTOToLanguageTransformer dtoTransformer = new LanguageDTOToLanguageTransformer();
	
	@Override
	public List<Language> convert(List<LanguageDTO> source) {
		List<Language> languages = new ArrayList<>();
		if (source != null) {
			for (LanguageDTO dto : source) {
				languages.add(dtoTransformer.convert(dto));
			}
		}
		return languages;
	}

}
