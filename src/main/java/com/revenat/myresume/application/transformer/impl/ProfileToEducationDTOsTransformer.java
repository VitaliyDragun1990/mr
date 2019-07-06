package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.domain.entity.Education;
import com.revenat.myresume.domain.entity.Profile;

@Deprecated
@TypeConverter
class ProfileToEducationDTOsTransformer implements Converter<Profile, List<EducationDTO>> {

	@Override
	public List<EducationDTO> convert(Profile source) {
		List<EducationDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Education education : source.getEducations()) {
				dtoList.add(new EducationDTO(education));
			}
		}
		return dtoList;
	}

}
