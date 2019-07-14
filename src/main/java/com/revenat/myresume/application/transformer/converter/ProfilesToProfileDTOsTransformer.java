package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Profile;

@TypeConverter
class ProfilesToProfileDTOsTransformer implements Converter<List<Profile>, List<ProfileDTO>> {
	private final ProfileToProfileDTOTransformer profileToDTOTransformer = new ProfileToProfileDTOTransformer();

	@Override
	public List<ProfileDTO> convert(List<Profile> source) {
		List<ProfileDTO> dtoList = new ArrayList<>();
		for (Profile profile : source) {
			dtoList.add(profileToDTOTransformer.convert(profile));
		}
		return dtoList;
	}

}
