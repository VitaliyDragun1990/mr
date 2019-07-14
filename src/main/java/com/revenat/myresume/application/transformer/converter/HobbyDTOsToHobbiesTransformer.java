package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Hobby;

@TypeConverter
class HobbyDTOsToHobbiesTransformer implements Converter<List<HobbyDTO>, List<Hobby>> {
	private HobbyDTOToHobbyTransformer dtoToHobbyTransformer = new HobbyDTOToHobbyTransformer();
	
	@Override
	public List<Hobby> convert(List<HobbyDTO> source) {
		List<Hobby> hobbies = new ArrayList<>();
		if (source != null) {
			for (HobbyDTO dto : source) {
				hobbies.add(dtoToHobbyTransformer.convert(dto));
			}
		}
		return hobbies;
	}

}
