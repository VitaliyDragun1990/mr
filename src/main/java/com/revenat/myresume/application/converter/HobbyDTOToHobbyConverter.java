package com.revenat.myresume.application.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.domain.entity.Hobby;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
public class HobbyDTOToHobbyConverter implements Converter<HobbyDTO, Hobby> {

	@Override
	public Hobby convert(HobbyDTO dto) {
		Hobby h = new Hobby();
		h.setId(dto.getId());
		h.setName(dto.getName());
		if (dto.getProfileId() != null) {
			Profile p = new Profile();
			p.setId(dto.getProfileId());
			h.setProfile(p);
		}
		return h;
	}

}
