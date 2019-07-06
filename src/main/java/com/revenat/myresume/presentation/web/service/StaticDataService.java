package com.revenat.myresume.presentation.web.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import com.revenat.myresume.application.dto.HobbyDTO;

/**
 * Provides access to static profile data.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface StaticDataService {

	@Nonnull
	Set<HobbyDTO> listAllHobbies();
	
	@Nonnull
	List<HobbyDTO> listAllHobbiesWithSelectedMarked(@Nonnull Collection<HobbyDTO> selectedHobbies);

	@Nonnull
	List<HobbyDTO> createHobbyListByNames(@Nonnull List<String> names);
	
	@Nonnull
	Map<Integer, String> mapMonths();

	@Nonnull
	List<Integer> listExperienceYears();

	@Nonnull
	List<Integer> listCoursesYears();

	@Nonnull
	List<Integer> listEducationYears();

	@Nonnull
	Collection<String> getAllSkillCategories();

	@Nonnull
	Collection<String> getLanguageTypes();

	@Nonnull
	Collection<String> getLanguageLevels();
}
