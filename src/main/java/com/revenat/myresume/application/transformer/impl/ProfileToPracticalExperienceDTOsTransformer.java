package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.ExperienceDTO;
import com.revenat.myresume.domain.entity.Experience;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
class ProfileToExperienceDTOsTransformer implements Converter<Profile, List<ExperienceDTO>> {

	@Override
	public List<ExperienceDTO> convert(Profile profile) {
		List<ExperienceDTO> dtoList = new ArrayList<>();
		for (Experience exp : profile.getExperience()) {
			dtoList.add(new ExperienceDTO(exp));
		}
		
		return dtoList;
	}

}
