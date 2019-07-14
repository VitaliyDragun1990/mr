package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.domain.document.Skill;

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
