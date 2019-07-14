package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Hobby;
import com.revenat.myresume.domain.document.Profile;

@TypeConverter
class ProfileToHobbyDTOsTransfromer implements Converter<Profile, List<HobbyDTO>> {

	@Override
	public List<HobbyDTO> convert(Profile profile) {
		List<HobbyDTO> dtoList = new ArrayList<>();
		if (profile != null) {
			for (Hobby hobby : profile.getHobbies()) {
				dtoList.add(new HobbyDTO(hobby));
			}
		}
		return dtoList;
	}

}
