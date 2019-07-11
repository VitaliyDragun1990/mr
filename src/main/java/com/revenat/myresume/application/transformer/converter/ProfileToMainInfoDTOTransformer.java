package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
class ProfileToMainInfoDTOTransformer implements Converter<Profile, MainInfoDTO> {

	@Override
	public MainInfoDTO convert(Profile source) {
		return new MainInfoDTO(source);
	}

}
