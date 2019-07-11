package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.domain.entity.Hobby;

@TypeConverter
class HobbyDTOsToHobbiesTransformer implements Converter<List<HobbyDTO>, List<Hobby>> {
	private HobbyDTOToHobbyTransformer dtoTransformer = new HobbyDTOToHobbyTransformer();
	
	@Override
	public List<Hobby> convert(List<HobbyDTO> source) {
		List<Hobby> hobbies = new ArrayList<>();
		if (source != null) {
			for (HobbyDTO dto : source) {
				hobbies.add(dtoTransformer.convert(dto));
			}
		}
		return hobbies;
	}

}
