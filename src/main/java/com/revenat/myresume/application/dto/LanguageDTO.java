package com.revenat.myresume.application.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import com.revenat.myresume.application.validation.annotation.EnglishLanguage;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.LanguageType;
import com.revenat.myresume.infrastructure.util.CommonUtils;

public class LanguageDTO implements Serializable, Comparable<LanguageDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank
	@Size(max = 60)
	@EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecSymbols = false)
	@SafeHtml
	private String name;
	
	@NotBlank
	@EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecSymbols = false)
	private String type;
	
	@NotBlank
	@EnglishLanguage(withNumbers = false, withPunctuations = false)
	private String level;
	
	public LanguageDTO() {
	}

	public LanguageDTO(Language entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.type = entity.getType().getType();
		this.level = entity.getLevel().getLevel();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public boolean isHasLanguageType() {
		return !type.equals(LanguageType.ALL.getType());
	}
	
	@Override
	public int compareTo(LanguageDTO o) {
		return getName().compareToIgnoreCase(o.getName());
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LanguageDTO))
			return false;
		LanguageDTO other = (LanguageDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
