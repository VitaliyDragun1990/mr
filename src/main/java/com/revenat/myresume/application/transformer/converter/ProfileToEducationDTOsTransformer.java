package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Education;
import com.revenat.myresume.domain.document.Profile;

@TypeConverter
class ProfileToEducationDTOsTransformer implements Converter<Profile, List<EducationDTO>> {

	@Override
	public List<EducationDTO> convert(Profile profile) {
		List<EducationDTO> dtoList = new ArrayList<>();
		if (profile != null) {
			for (Education education : profile.getEducations()) {
				dtoList.add(new EducationDTO(education));
			}
		}
		return dtoList;
	}

}
