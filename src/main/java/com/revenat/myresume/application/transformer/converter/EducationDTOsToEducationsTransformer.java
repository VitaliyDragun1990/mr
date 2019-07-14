package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Education;

@TypeConverter
class EducationDTOsToEducationsTransformer implements Converter<List<EducationDTO>, List<Education>> {
	private EducationDTOToEducationTransformer dtoToEducationTransformer = new EducationDTOToEducationTransformer();
	
	@Override
	public List<Education> convert(List<EducationDTO> source) {
		List<Education> education = new ArrayList<>();
		if (source != null) {
			for (EducationDTO dto : source) {
				education.add(dtoToEducationTransformer.convert(dto));
			}
		}
		return education;
	}

}
