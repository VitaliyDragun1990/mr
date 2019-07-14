package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Profile;

@TypeConverter
class ProfileToMainInfoDTOTransformer implements Converter<Profile, MainInfoDTO> {

	@Override
	public MainInfoDTO convert(Profile profile) {
		return new MainInfoDTO(profile);
	}

}
