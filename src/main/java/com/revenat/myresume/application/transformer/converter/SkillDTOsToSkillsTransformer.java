package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Skill;

@TypeConverter
class SkillDTOsToSkillsTransformer implements Converter<List<SkillDTO>, List<Skill>> {
	private SkillDTOToSkillTransformer dtoToSkillTransformer = new SkillDTOToSkillTransformer();
	
	@Override
	public List<Skill> convert(List<SkillDTO> source) {
		List<Skill> skills = new ArrayList<>();
		if (source != null) {
			for (SkillDTO dto : source) {
				skills.add(dtoToSkillTransformer.convert(dto));
			}
		}
		return skills;
	}
}
