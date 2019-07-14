package com.revenat.myresume.domain.document;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revenat.myresume.infrastructure.util.CommonUtils;

public class Course implements ProfileDocument, Serializable {
	private static final long serialVersionUID = 6725374949235352235L;
	
	private String name;
	
	private String school;
	
	@JsonIgnore
	private LocalDate endDate;

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

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
	
}
