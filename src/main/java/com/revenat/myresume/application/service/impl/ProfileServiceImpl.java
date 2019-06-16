package com.revenat.myresume.application.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.application.service.ProfileService;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.Skill;
import com.revenat.myresume.domain.entity.SkillCategory;
import com.revenat.myresume.infrastructure.repository.ProfileRepository;

@Service
class ProfileServiceImpl implements ProfileService {
	
	private final ProfileRepository profileRepo;
	private final ConversionService conversionService;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepo, ConversionService conversionService) {
		this.profileRepo = profileRepo;
		this.conversionService = conversionService;
	}

	@Override
	public ProfileDTO getByUid(String uid) {
		Optional<Profile> profile = profileRepo.findOneByUid(uid);
		return conversionService.convert(profile.get(), ProfileDTO.class);
	}

	@Override
	public List<SkillDTO> getSkillsFor(String uid) {
		Optional<Profile> profile = profileRepo.findOneByUid(uid);
		List<SkillDTO> dtoList = new ArrayList<>();
		
		if (profile.isPresent()) {
			Profile p = profile.get();
			List<Skill> skills = profile.get().getSkills();
			for (Skill skill : skills) {
				SkillDTO dto = new SkillDTO(skill, p.getId());
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	
	@Override
	public List<String> getAllSkillCategories() {
		List<String> skillCategories = new ArrayList<>();
		for(SkillCategory category : SkillCategory.values()) {
			skillCategories.add(category.getCategory());
		}
		
		return skillCategories;
	}

}
