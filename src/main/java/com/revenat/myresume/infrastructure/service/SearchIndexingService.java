package com.revenat.myresume.infrastructure.service;

import java.util.List;

import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Experience;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.Skill;

public interface SearchIndexingService {

	void createIndexProfile(Profile profile);
	
	void updateIndexProfile(Profile profile);
	
	void updateIndexProfileSkills(long profileId, List<Skill> updatedData);
	
	void updateIndexProfileCertificates(long profileId, List<Certificate> updatedData);
	
	void updateIndexProfileCourses(long profileId, List<Course> updatedData);
	
	void updateIndexProfileExperience(long profileId, List<Experience> updatedData);
	
	void updateIndexProfileLanguages(long profileId, List<Language> updatedData);
}
