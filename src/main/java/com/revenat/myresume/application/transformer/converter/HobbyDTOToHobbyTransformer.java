package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Hobby;

@TypeConverter
class HobbyDTOToHobbyTransformer implements Converter<HobbyDTO, Hobby> {

	@Override
	public Hobby convert(HobbyDTO dto) {
		Hobby h = new Hobby();
		h.setName(dto.getName());
		return h;
	}

}
