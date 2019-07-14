package com.revenat.myresume.domain.document;

import java.io.Serializable;

import com.revenat.myresume.infrastructure.util.CommonUtils;

public class Education implements ProfileDocument, Serializable {
	private static final long serialVersionUID = -5164467264416703233L;
	
	private String summary;
	
	private Integer startYear;
	
	private Integer endYear;
	
	private String university;
	
	private String faculty;

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
	public String toString() {
		return CommonUtils.toString(this);
	}
	
}
