package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.domain.entity.Hobby;

@TypeConverter
class HobbiesToHobbyDTOsTransformer implements Converter<List<Hobby>, List<HobbyDTO>> {
	
	@Override
	public List<HobbyDTO> convert(List<Hobby> source) {
		List<HobbyDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Hobby h : source) {
				dtoList.add(new HobbyDTO(h));
			}
		}
		return dtoList;
	}

}
