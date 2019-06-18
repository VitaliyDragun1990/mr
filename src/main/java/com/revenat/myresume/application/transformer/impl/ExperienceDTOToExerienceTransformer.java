package com.revenat.myresume.application.transformer.impl;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.ExperienceDTO;
import com.revenat.myresume.domain.entity.Experience;

@TypeConverter
class ExperienceDTOToExerienceTransformer implements Converter<ExperienceDTO, Experience> {

	@Override
	public Experience convert(ExperienceDTO dto) {
		Experience e = new Experience();
		e.setCompany(dto.getCompany());
		e.setDemo(dto.getDemo());
		e.setEndDate(dto.getEndDate());
		e.setId(dto.getId());
		e.setPosition(dto.getPosition());
		e.setResponsibilities(dto.getResponsibilities());
		e.setSourceCode(dto.getSourceCode());
		e.setStartDate(dto.getStartDate());
		return e;
	}

}
