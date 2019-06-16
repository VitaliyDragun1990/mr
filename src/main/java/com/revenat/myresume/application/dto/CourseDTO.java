package com.revenat.myresume.application.dto;

import java.io.Serializable;

import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.infrastructure.util.CommonUtil;

public class CourseDTO extends AbstractEndDateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private String name;
	private String school;
	
	public CourseDTO() {
	}
	
	public CourseDTO(Course entity, Long profileId) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.profileId = profileId;
		this.school = entity.getSchool();
		this.setEndDate(entity.getEndDate());
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
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof CourseDTO))
			return false;
		CourseDTO other = (CourseDTO) obj;
		if (getEndDate() == null) {
			if (other.getEndDate() != null)
				return false;
		} else if (!getEndDate().equals(other.getEndDate()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return CommonUtil.toString(this);
	}
	
}
