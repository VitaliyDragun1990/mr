package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Skill;
import com.revenat.myresume.domain.document.SkillCategory;

@TypeConverter
class SkillDTOToSkillTransformer implements Converter<SkillDTO, Skill> {

	@Override
	public Skill convert(SkillDTO dto) {
		Skill s = new Skill();
		s.setCategory(SkillCategory.getCategory(dto.getCategory()));
		s.setValue(dto.getValue());
		return s;
	}

}
