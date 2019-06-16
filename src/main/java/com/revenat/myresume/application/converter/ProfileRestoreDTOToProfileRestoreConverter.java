package com.revenat.myresume.application.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.ProfileRestoreDTO;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.ProfileRestore;

@TypeConverter
public class ProfileRestoreDTOToProfileRestoreConverter implements Converter<ProfileRestoreDTO, ProfileRestore> {

	@Override
	public ProfileRestore convert(ProfileRestoreDTO dto) {
		ProfileRestore pr = new ProfileRestore();
		pr.setToken(dto.getToken());
		Profile p = new Profile();
		p.setId(dto.getId());
		pr.setProfile(p);
		pr.setId(dto.getId());
		return pr;
	}

}
