package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Language;
import com.revenat.myresume.domain.document.Profile;

@TypeConverter
class ProfileToLanguageDTOsTransformer implements Converter<Profile, List<LanguageDTO>> {

	@Override
	public List<LanguageDTO> convert(Profile profile) {
		List<LanguageDTO> dtoList = new ArrayList<>();
		if (profile != null) {
			for (Language language : profile.getLanguages()) {
				dtoList.add(new LanguageDTO(language));
			}
		}
		return dtoList;
	}

}
