package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.PracticalExperience;

@TypeConverter
class PracticalExperienceDTOsToPracticalExperiencesTransformer implements Converter<List<PracticalExperienceDTO>, List<PracticalExperience>> {
	private PracticalExperienceDTOToPracticaExperienceTransformer dtoToExperienceTransformer = new PracticalExperienceDTOToPracticaExperienceTransformer();;
	
	@Override
	public List<PracticalExperience> convert(List<PracticalExperienceDTO> source) {
		List<PracticalExperience> experience = new ArrayList<>();
		if (source != null) {
			for (PracticalExperienceDTO dto : source) {
				experience.add(dtoToExperienceTransformer.convert(dto));
			}
		}
		return experience;
	}

}
