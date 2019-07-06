package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.Profile;

@Deprecated
@TypeConverter
class ProfileToLanguageDTOsTransformer implements Converter<Profile, List<LanguageDTO>> {

	@Override
	public List<LanguageDTO> convert(Profile source) {
		List<LanguageDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Language language : source.getLanguages()) {
				dtoList.add(new LanguageDTO(language));
			}
		}
		return dtoList;
	}

}
