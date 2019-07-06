package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.domain.entity.Education;

@TypeConverter
class EducationsToEducationDTOsTransformer implements Converter<List<Education>, List<EducationDTO>> {
	
	@Override
	public List<EducationDTO> convert(List<Education> source) {
		List<EducationDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Education e : source) {
				dtoList.add(new EducationDTO(e));
			}
		}
		return dtoList;
	}

}
