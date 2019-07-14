package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Education;

@TypeConverter
class EducationDTOToEducationTransformer implements Converter<EducationDTO, Education> {

	@Override
	public Education convert(EducationDTO dto) {
		Education e = new Education();
		e.setEndYear(dto.getEndYear());
		e.setFaculty(dto.getFaculty());
		e.setStartYear(dto.getStartYear());
		e.setSummary(dto.getSummary());
		e.setUniversity(dto.getUniversity());
		
		return e;
	}

}
