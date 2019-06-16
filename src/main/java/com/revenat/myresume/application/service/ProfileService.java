package com.revenat.myresume.application.service;

import java.util.List;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.dto.SkillDTO;

public interface ProfileService {

	ProfileDTO getByUid(String uid);
	
	List<SkillDTO> getSkillsFor(String uid);
	
	List<String> getAllSkillCategories();
}
