package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.Skill;

@TypeConverter
class ProfileToSkillDTOsTransformer implements Converter<Profile, List<SkillDTO>> {

	@Override
	public List<SkillDTO> convert(Profile profile) {
		List<SkillDTO> dtoList = new ArrayList<>();
		List<Skill> skills = profile.getSkills();
		for (Skill skill : skills) {
			SkillDTO dto = new SkillDTO(skill);
			dtoList.add(dto);
		}
		return dtoList;
	}

}
