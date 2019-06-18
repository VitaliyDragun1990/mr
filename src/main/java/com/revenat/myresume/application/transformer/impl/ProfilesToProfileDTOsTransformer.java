package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
class ProfilesToProfileDTOsTransformer implements Converter<List<Profile>, List<ProfileDTO>> {
	private final ProfileToProfileDTOTransformer profileConverter = new ProfileToProfileDTOTransformer();

	@Override
	public List<ProfileDTO> convert(List<Profile> source) {
		List<ProfileDTO> dtoList = new ArrayList<>();
		for (Profile profile : source) {
			dtoList.add(profileConverter.convert(profile));
		}
		return dtoList;
	}

}
