package com.revenat.myresume.application.dto;

import java.io.Serializable;

import com.revenat.myresume.domain.entity.Education;
import com.revenat.myresume.infrastructure.util.CommonUtil;

public class EducationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private String summary;
	private Integer startYear;
	private Integer endYear;
	private String university;
	private String faculty;
	
	public EducationDTO() {
	}

	public EducationDTO(Education entity, Long profileId) {
		this.id = entity.getId();
		this.profileId = profileId;
		this.summary = entity.getSummary();
		this.startYear = entity.getStartYear();
		this.endYear = entity.getEndYear();
		this.university = entity.getUniversity();
		this.faculty = entity.getFaculty();
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((startYear == null) ? 0 : startYear.hashCode());
		result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
		result = prime * result + ((startYear == null) ? 0 : startYear.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((university == null) ? 0 : university.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EducationDTO))
			return false;
		EducationDTO other = (EducationDTO) obj;
		if (startYear == null) {
			if (other.startYear != null)
				return false;
		} else if (!startYear.equals(other.startYear))
			return false;
		if (faculty == null) {
			if (other.faculty != null)
				return false;
		} else if (!faculty.equals(other.faculty))
			return false;
		if (endYear == null) {
			if (other.endYear != null)
				return false;
		} else if (!endYear.equals(other.endYear))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		if (university == null) {
			if (other.university != null)
				return false;
		} else if (!university.equals(other.university))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return CommonUtil.toString(this);
	}

}
