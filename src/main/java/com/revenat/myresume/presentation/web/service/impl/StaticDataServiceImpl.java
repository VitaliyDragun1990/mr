package com.revenat.myresume.presentation.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.domain.document.LanguageLevel;
import com.revenat.myresume.domain.document.LanguageType;
import com.revenat.myresume.domain.document.SkillCategory;
import com.revenat.myresume.infrastructure.util.Checks;
import com.revenat.myresume.infrastructure.util.CommonUtils;
import com.revenat.myresume.presentation.web.service.StaticDataService;

@Service
class StaticDataServiceImpl implements StaticDataService {

	private final int experienceYearsAgo;
	private final int educationYearsAgo;
	private final int courseYearsAgo;
	
	private final Set<HobbyDTO> allHobbies;
	private final Set<String> allHobbyNames;
	
	@Autowired
	public StaticDataServiceImpl(
			@Value("${experience.years.ago}") int experienceYearsAgo,
			@Value("${education.years.ago}") int educationYearsAgo,
			@Value("${course.years.ago}") int courseYearsAgo) {
		this.experienceYearsAgo = experienceYearsAgo;
		this.educationYearsAgo = educationYearsAgo;
		this.courseYearsAgo = courseYearsAgo;
		this.allHobbies = CommonUtils.getSafeSet(createAllHobbiesSet());
		this.allHobbyNames = CommonUtils.getSafeSet(createAllHobbyNamesSet());
	}

	private Set<String> createAllHobbyNamesSet() {
		Set<String> set = new HashSet<>();
		for (HobbyDTO h : allHobbies) {
			set.add(h.getName());
		}
		return set;
	}

	private Set<HobbyDTO> createAllHobbiesSet() {
		return new TreeSet<>(Arrays.asList(
				new HobbyDTO("Cycling"),
				new HobbyDTO("Handball"),
				new HobbyDTO("Football"),
				new HobbyDTO("Basketball"),
				new HobbyDTO("Bowling"),
				new HobbyDTO("Boxing"),
				new HobbyDTO("Volleyball"),
				new HobbyDTO("Baseball"),
				new HobbyDTO("Skating"),
				new HobbyDTO("Skiing"),
				new HobbyDTO("Table tennis"),
				new HobbyDTO("Tennis"),
				new HobbyDTO("Weightlifting"),
				new HobbyDTO("Automobiles"),
				new HobbyDTO("Book reading"),
				new HobbyDTO("Cricket"),
				new HobbyDTO("Photo"),
				new HobbyDTO("Shopping"),
				new HobbyDTO("Cooking"),
				new HobbyDTO("Codding"),
				new HobbyDTO("Animals"),
				new HobbyDTO("Traveling"),
				new HobbyDTO("Movie"),
				new HobbyDTO("Painting"),
				new HobbyDTO("Darts"),
				new HobbyDTO("Fishing"),
				new HobbyDTO("Kayak slalom"),
				new HobbyDTO("Games of chance"),
				new HobbyDTO("Ice hockey"),
				new HobbyDTO("Roller skating"),
				new HobbyDTO("Swimming"),
				new HobbyDTO("Diving"),
				new HobbyDTO("Golf"),
				new HobbyDTO("Shooting"),
				new HobbyDTO("Rowing"),
				new HobbyDTO("Camping"),
				new HobbyDTO("Archery"),
				new HobbyDTO("Pubs"),
				new HobbyDTO("Music"),
				new HobbyDTO("Computer games"),
				new HobbyDTO("Authorship"),
				new HobbyDTO("Singing"),
				new HobbyDTO("Foreign lang"),
				new HobbyDTO("Billiards"),
				new HobbyDTO("Skateboarding"),
				new HobbyDTO("Collecting"),
				new HobbyDTO("Badminton"),
				new HobbyDTO("Disco")
				));
	}

	private Set<HobbyDTO> listAllHobbies() {
		return allHobbies;
	}
	
	@Override
	public List<HobbyDTO> getAllHobbiesWithSelectedMarked(Collection<HobbyDTO> selectedHobbies) {
		Checks.checkParam(selectedHobbies != null, "selectedHobbies can not be null");
		
		List<HobbyDTO> hobbies = new ArrayList<>();
		for (HobbyDTO h : listAllHobbies()) {
			boolean selected = selectedHobbies.contains(h);
			hobbies.add(new HobbyDTO(h.getName(), selected));
		}
		return hobbies;
	}

	@Override
	public List<HobbyDTO> getSelectedHobbiesByName(List<String> hobbyNames) {
		Checks.checkParam(hobbyNames != null, "hobbyNames ca not be null");
		
		List<HobbyDTO> result = new ArrayList<>();
		for (String name : hobbyNames) {
			if (allHobbyNames.contains(name)) {
				result.add(new HobbyDTO(name, true));
			}
		}
		return result;
	}
	
	@Override
	public Map<Integer, String> getMonthMap() {
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December"};
		Map<Integer, String> map = new LinkedHashMap<>();
		for (int i = 0; i < months.length; i++) {
			map.put(i + 1, months[i]);
		}
		return map;
	}

	@Override
	public List<Integer> getExperienceYears() {
		return listYears(experienceYearsAgo);
	}

	@Override
	public List<Integer> getCoursesYears() {
		return listYears(courseYearsAgo);
	}

	@Override
	public List<Integer> getEducationYears() {
		return listYears(educationYearsAgo);
	}
	
	private List<Integer> listYears(int count) {
		List<Integer> years = new ArrayList<>();
		int now = LocalDate.now().getYear();
		for (int i = 0; i < count; i++) {
			years.add(now - i);
		}
		return years;
	}

	@Override
	public Collection<String> getAllSkillCategories() {
		List<String> skillCategories = new ArrayList<>();
		for (SkillCategory category : SkillCategory.values()) {
			skillCategories.add(category.getCategory());
		}
		return skillCategories;
	}

	@Override
	public Collection<String> getAllLanguageLevels() {
		List<String> languageLevels = new ArrayList<>();
		for (LanguageLevel level : LanguageLevel.values()) {
			languageLevels.add(level.getLevel());
		}
		return languageLevels;
	}

	@Override
	public Collection<String> getAllLanguageTypes() {
		List<String> languageTypes = new ArrayList<>();
		for (LanguageType type : LanguageType.values()) {
			languageTypes.add(type.getType());
		}
		return languageTypes;
	}

}
