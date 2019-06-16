package com.revenat.myresume.application.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.LanguageLevel;
import com.revenat.myresume.domain.entity.LanguageType;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
public class LanguageDTOToLanguageConverter implements Converter<LanguageDTO, Language> {

	@Override
	public Language convert(LanguageDTO dto) {
		Language l = new Language();
		l.setId(dto.getId());
		l.setName(dto.getName());
		l.setLevel(LanguageLevel.getLevel(dto.getLevel()));
		l.setType(LanguageType.getType(dto.getType()));
		if (dto.getProfileId() != null) {
			Profile p = new Profile();
			p.setId(dto.getProfileId());
			l.setProfile(p);
		}
		return l;
	}

}
