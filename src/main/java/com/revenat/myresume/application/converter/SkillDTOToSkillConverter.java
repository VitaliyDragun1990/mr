package com.revenat.myresume.application.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.Skill;
import com.revenat.myresume.domain.entity.SkillCategory;

@TypeConverter
public class SkillDTOToSkillConverter implements Converter<SkillDTO, Skill> {

	@Override
	public Skill convert(SkillDTO dto) {
		Skill s = new Skill();
		s.setId(dto.getId());
		s.setCategory(SkillCategory.getCategory(dto.getCategory()));
		s.setValue(dto.getValue());
		if (dto.getProfileId() != null) {
			Profile p = new Profile();
			p.setId(dto.getProfileId());
			s.setProfile(p);
		}
		return s;
	}

}
