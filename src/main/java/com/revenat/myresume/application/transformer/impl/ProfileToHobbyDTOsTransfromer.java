package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.domain.entity.Hobby;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
class ProfileToHobbyDTOsTransfromer implements Converter<Profile, List<HobbyDTO>> {

	@Override
	public List<HobbyDTO> convert(Profile source) {
		List<HobbyDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Hobby hobby : source.getHobbies()) {
				dtoList.add(new HobbyDTO(hobby));
			}
		}
		return dtoList;
	}

}
