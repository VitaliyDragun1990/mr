package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Language;

@TypeConverter
class LanguagesToLanguageDTOsTransformer implements Converter<List<Language>, List<LanguageDTO>> {
	
	@Override
	public List<LanguageDTO> convert(List<Language> source) {
		List<LanguageDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Language l : source) {
				dtoList.add(new LanguageDTO(l));
			}
		}
		return dtoList;
	}

}
