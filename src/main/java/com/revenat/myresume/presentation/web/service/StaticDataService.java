package com.revenat.myresume.presentation.web.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.revenat.myresume.application.dto.HobbyDTO;

/**
 * Provides access to static profile-related data.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface StaticDataService {

	/**
	 * Returns list with all hobbies registered in the application. Those hobbies
	 * that have a match among provided {@code selectedHobbies} will be marked as
	 * {@code selected}.
	 * 
	 * @param selectedHobbies particular profile's hobbies
	 * @return list with all hobbies registered in the application with selected
	 *         marked.
	 */
	@Nonnull
	List<HobbyDTO> getAllHobbiesWithSelectedMarked(@Nonnull Collection<HobbyDTO> selectedHobbies);

	/**
	 * Returns list of hobbies with names corresponding to specified {@code names}
	 * argument.
	 * 
	 * @param names names of the selected hobbies
	 * @return list of hobbies
	 */
	@Nonnull
	List<HobbyDTO> getSelectedHobbiesByName(@Nonnull List<String> names);

	/**
	 * Returns a map containing key-value pairs in form of 'month ordinal number -
	 * month name'
	 * 
	 * @return
	 */
	@Nonnull
	Map<Integer, String> getMonthMap();

	/**
	 * Returns application approved year values user can specify for his practical
	 * experience
	 * 
	 * @return
	 */
	@Nonnull
	List<Integer> getExperienceYears();

	/**
	 * Returns application approved year values user can specify for his practical
	 * experience
	 * 
	 * @return
	 */
	@Nonnull
	List<Integer> getCoursesYears();

	/**
	 * Returns application approved year values user can specify for his education
	 * 
	 * @return
	 */
	@Nonnull
	List<Integer> getEducationYears();

	/**
	 * Returns names of all application approved skill categories
	 * @return
	 */
	@Nonnull
	Collection<String> getAllSkillCategories();

	/**
	 * Returns names of all application approved language types
	 * @return
	 */
	@Nonnull
	Collection<String> getAllLanguageTypes();

	/**
	 * Returns names of all application approved language levels
	 * @return
	 */
	@Nonnull
	Collection<String> getAllLanguageLevels();
}
