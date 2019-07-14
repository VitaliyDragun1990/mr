package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.PracticalExperience;
import com.revenat.myresume.domain.document.Profile;

@TypeConverter
class ProfileToPracticalExperienceDTOsTransformer implements Converter<Profile, List<PracticalExperienceDTO>> {

	@Override
	public List<PracticalExperienceDTO> convert(Profile profile) {
		List<PracticalExperienceDTO> dtoList = new ArrayList<>();
		for (PracticalExperience exp : profile.getExperience()) {
			dtoList.add(new PracticalExperienceDTO(exp));
		}
		
		return dtoList;
	}

}
