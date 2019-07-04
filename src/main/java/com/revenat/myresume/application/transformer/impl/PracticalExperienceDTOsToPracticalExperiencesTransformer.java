package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.ExperienceDTO;
import com.revenat.myresume.domain.entity.Experience;

@TypeConverter
class ExperienceDTOsToExperiencesTransformer implements Converter<List<ExperienceDTO>, List<Experience>> {
	private ExperienceDTOToExerienceTransformer dtoTransformer = new ExperienceDTOToExerienceTransformer();;
	
	@Override
	public List<Experience> convert(List<ExperienceDTO> source) {
		List<Experience> experience = new ArrayList<>();
		if (source != null) {
			for (ExperienceDTO dto : source) {
				experience.add(dtoTransformer.convert(dto));
			}
		}
		return experience;
	}

}
