package com.revenat.myresume.application.transformer.impl;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.domain.entity.Hobby;

@TypeConverter
class HobbyDTOToHobbyTransformer implements Converter<HobbyDTO, Hobby> {

	@Override
	public Hobby convert(HobbyDTO dto) {
		Hobby h = new Hobby();
		h.setId(dto.getId());
		h.setName(dto.getName());
		return h;
	}

}
