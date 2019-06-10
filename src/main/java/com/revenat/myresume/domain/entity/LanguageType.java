package com.revenat.myresume.domain.entity;

import java.util.HashMap;
import java.util.Map;

public enum LanguageType {
	ALL("all"),
	SPOKEN("spoken"),
	WRITTEN("written");
	
	private static final Map<String, LanguageType> typeMapping = new HashMap<>();
	
	static {
		typeMapping.put(ALL.type, ALL);
		typeMapping.put(SPOKEN.type, SPOKEN);
		typeMapping.put(WRITTEN.type, WRITTEN);
	}
	
	public static LanguageType getType(String type) {
		if (typeMapping.get(type) == null) {
			throw new IllegalArgumentException(String.format("There is no LanguageType mapping with type (%s)", type));
		}
		return typeMapping.get(type);
	}
	
	private LanguageType(String type) {
		this.type = type;
	}

	private final String type;
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
