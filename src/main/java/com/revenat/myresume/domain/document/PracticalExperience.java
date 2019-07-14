package com.revenat.myresume.domain.document;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revenat.myresume.infrastructure.util.CommonUtils;

public class PracticalExperience implements ProfileDocument, Serializable {
	private static final long serialVersionUID = 5676683812478918147L;

	private String position;

	private String company;

	@JsonIgnore
	private LocalDate startDate;

	@JsonIgnore
	private LocalDate endDate;

	private String responsibilities;

	@JsonIgnore
	private String demo;

	@JsonIgnore
	private String sourceCode;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
