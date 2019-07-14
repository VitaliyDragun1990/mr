package com.revenat.myresume.domain.document;

import java.util.HashMap;
import java.util.Map;

public enum SkillCategory {
	LANGUAGES("Languages"),
	DBMS("DBMS"),
	FRONTEND("Frontend"),
	BACKEND("Backend"),
	IDE("IDE"),
	CVS("CVS"),
	WEB_SERVERS("Web Servers"),
	BUILD_SYSTEMS("Build systems"),
	CLOUD("Cloud");
	
	private static final Map<String, SkillCategory> categoryMapping = new HashMap<>();
	
	static {
		categoryMapping.put(LANGUAGES.category, LANGUAGES);
		categoryMapping.put(DBMS.category, DBMS);
		categoryMapping.put(FRONTEND.category, FRONTEND);
		categoryMapping.put(BACKEND.category, BACKEND);
		categoryMapping.put(IDE.category, IDE);
		categoryMapping.put(CVS.category, CVS);
		categoryMapping.put(WEB_SERVERS.category, WEB_SERVERS);
		categoryMapping.put(BUILD_SYSTEMS.category, BUILD_SYSTEMS);
		categoryMapping.put(CLOUD.category, CLOUD);
	}
	
	public static SkillCategory getCategory(String category) {
		if (categoryMapping.get(category) == null) {
			throw new IllegalArgumentException(String.format("There is no SkillCategory mapping with category (%s)", category));
		}
		return categoryMapping.get(category);
	}
	
	private SkillCategory(String category) {
		this.category = category;
	}

	private final String category;
	
	public String getCategory() {
		return category;
	}
	
	@Override
	public String toString() {
		return category;
	}
}
