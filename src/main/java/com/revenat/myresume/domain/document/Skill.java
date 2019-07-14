package com.revenat.myresume.domain.document;

import java.io.Serializable;

import com.revenat.myresume.infrastructure.util.CommonUtils;

public class Skill implements ProfileDocument, Serializable {
	private static final long serialVersionUID = -8068581222220250534L;
	
	private SkillCategory category;
	
	private String value;

	public SkillCategory getCategory() {
		return category;
	}

	public void setCategory(SkillCategory category) {
		this.category = category;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
