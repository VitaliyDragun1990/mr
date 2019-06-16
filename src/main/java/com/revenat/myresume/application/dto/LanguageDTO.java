package com.revenat.myresume.application.dto;

import java.io.Serializable;

import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.LanguageType;
import com.revenat.myresume.infrastructure.util.CommonUtil;

public class LanguageDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private String name;
	private String type;
	private String level;
	
	public LanguageDTO() {
	}

	public LanguageDTO(Language entity, Long profileId) {
		this.id = entity.getId();
		this.profileId = profileId;
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

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
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
	public String toString() {
		return CommonUtil.toString(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		if (!super.equals(obj))
			return false;
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
		if (type != other.type)
			return false;
		return true;
	}
}
