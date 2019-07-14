package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.PracticalExperience;

@TypeConverter
class PracticalExperiencesToPracticalExperienceDTOsTransformer implements Converter<List<PracticalExperience>, List<PracticalExperienceDTO>> {
	
	@Override
	public List<PracticalExperienceDTO> convert(List<PracticalExperience> source) {
		List<PracticalExperienceDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (PracticalExperience p : source) {
				dtoList.add(new PracticalExperienceDTO(p));
			}
		}
		return dtoList;
	}

}
