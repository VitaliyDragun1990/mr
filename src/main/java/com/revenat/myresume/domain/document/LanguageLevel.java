package com.revenat.myresume.domain.document;

import java.util.HashMap;
import java.util.Map;

public enum LanguageLevel {
	BEGINNER("beginner"),
	ELEMENTARY("elementary"),
	PRE_INTERMEDIATE("pre_intermediate"),
	INTERMEDIATE("intermediate"),
	UPPER_INTERMEDIATE("upper_intermediate"),
	ADVANCED("advanced"),
	PROFICIENCY("proficiency");
	
	private static final Map<String, LanguageLevel> levelMapping = new HashMap<>();
	
	static {
		levelMapping.put(BEGINNER.level, BEGINNER);
		levelMapping.put(ELEMENTARY.level, ELEMENTARY);
		levelMapping.put(PRE_INTERMEDIATE.level, PRE_INTERMEDIATE);
		levelMapping.put(INTERMEDIATE.level, INTERMEDIATE);
		levelMapping.put(UPPER_INTERMEDIATE.level, UPPER_INTERMEDIATE);
		levelMapping.put(ADVANCED.level, ADVANCED);
		levelMapping.put(PROFICIENCY.level, PROFICIENCY);
	}
	
	public static LanguageLevel getLevel(String level) {
		if (levelMapping.get(level) == null) {
			throw new IllegalArgumentException(String.format("There is no LanguageLevel mapping with level (%s)", level));
		}
		return levelMapping.get(level);
	}
	
	private LanguageLevel(String level) {
		this.level = level;
	}

	private final String level;
	
	public String getLevel() {
		return level;
	}
	
	@Override
	public String toString() {
		return level;
	}
}
