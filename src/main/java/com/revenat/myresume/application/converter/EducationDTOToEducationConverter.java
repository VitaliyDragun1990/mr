package com.revenat.myresume.application.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.domain.entity.Education;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
public class EducationDTOToEducationConverter implements Converter<EducationDTO, Education> {

	@Override
	public Education convert(EducationDTO dto) {
		Education e = new Education();
		e.setEndYear(dto.getEndYear());
		e.setFaculty(dto.getFaculty());
		e.setId(dto.getId());
		e.setStartYear(dto.getStartYear());
		e.setSummary(dto.getSummary());
		e.setUniversity(dto.getUniversity());
		if (dto.getProfileId() != null) {
			Profile p = new Profile();
			p.setId(dto.getProfileId());
			e.setProfile(p);
		}
		
		return e;
	}

}
