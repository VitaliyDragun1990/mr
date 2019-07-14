package com.revenat.myresume.application.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.SafeHtml;

import com.revenat.myresume.application.validation.annotation.EnglishLanguage;
import com.revenat.myresume.domain.document.Skill;
import com.revenat.myresume.domain.document.SkillCategory;
import com.revenat.myresume.infrastructure.util.CommonUtils;

public class SkillDTO implements Serializable, Comparable<SkillDTO> {
	private static final long serialVersionUID = 1L;

	@SafeHtml
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	private String category;
	
	@SafeHtml
	@EnglishLanguage
	private String value;

	public SkillDTO() {
	}
	
	public SkillDTO(Skill entity) {
		if (entity.getCategory() != null) {
			this.category = entity.getCategory().getCategory();
		}
		this.value = entity.getValue();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SkillDTO))
			return false;
		SkillDTO other = (SkillDTO) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(SkillDTO o) {
		return SkillCategory.getCategory(category).compareTo(SkillCategory.getCategory(o.getCategory()));
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}

}
