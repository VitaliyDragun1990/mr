package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Skill;

@TypeConverter
class SkillsToSkillDTOsTransformer implements Converter<List<Skill>, List<SkillDTO>> {
	
	@Override
	public List<SkillDTO> convert(List<Skill> source) {
		List<SkillDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Skill s : source) {
				dtoList.add(new SkillDTO(s));
			}
		}
		Collections.sort(dtoList);
		return dtoList;
	}
}
