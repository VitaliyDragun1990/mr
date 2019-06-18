package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.domain.entity.Skill;

@TypeConverter
class SkillDTOsToSkillsTransformer implements Converter<List<SkillDTO>, List<Skill>> {
	private SkillDTOToSkillTransformer skillConverter = new SkillDTOToSkillTransformer();
	
	@Override
	public List<Skill> convert(List<SkillDTO> source) {
		List<Skill> skills = new ArrayList<>();
		if (source != null) {
			for (SkillDTO dto : source) {
				skills.add(skillConverter.convert(dto));
			}
		}
		return skills;
	}
}
