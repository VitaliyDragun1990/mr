package com.revenat.myresume.domain.document;

import java.io.Serializable;

import com.revenat.myresume.infrastructure.util.CommonUtils;

public class Language implements ProfileDocument, Serializable {
	private static final long serialVersionUID = -5638604441701746453L;
	
	private String name;
	
	private LanguageType type;
	
	private LanguageLevel level;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LanguageType getType() {
		return type;
	}

	public void setType(LanguageType type) {
		this.type = type;
	}

	public LanguageLevel getLevel() {
		return level;
	}

	public void setLevel(LanguageLevel level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
