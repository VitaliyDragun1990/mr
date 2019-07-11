package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.domain.entity.PracticalExperience;

@TypeConverter
class PracticalExperienceDTOsToPracticalExperiencesTransformer implements Converter<List<PracticalExperienceDTO>, List<PracticalExperience>> {
	private PracticalExperienceDTOToPracticaExperienceTransformer dtoTransformer = new PracticalExperienceDTOToPracticaExperienceTransformer();;
	
	@Override
	public List<PracticalExperience> convert(List<PracticalExperienceDTO> source) {
		List<PracticalExperience> experience = new ArrayList<>();
		if (source != null) {
			for (PracticalExperienceDTO dto : source) {
				experience.add(dtoTransformer.convert(dto));
			}
		}
		return experience;
	}

}
