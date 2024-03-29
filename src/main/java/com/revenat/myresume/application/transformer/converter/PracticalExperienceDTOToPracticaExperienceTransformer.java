package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.domain.entity.PracticalExperience;

@TypeConverter
class PracticalExperienceDTOToPracticaExperienceTransformer implements Converter<PracticalExperienceDTO, PracticalExperience> {

	@Override
	public PracticalExperience convert(PracticalExperienceDTO dto) {
		PracticalExperience e = new PracticalExperience();
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
