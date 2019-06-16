package com.revenat.myresume.application.dto;

import java.time.LocalDate;

public class AbstractEndDateDTO {

	private LocalDate endDate;
	private Integer endDateMonth;
	private Integer endDateYear;
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public boolean isEnded() {
		return endDate != null;
	}
	
	public Integer getEndDateMonth() {
		if (endDate != null) {
			return endDate.getMonthValue();
		} else {
			return null;
		}
	}
	
	public Integer getEndDateYear() {
		if (endDate != null) {
			return endDate.getYear();
		} else {
			return null;
		}
	}
	
	public void setEndDateMonth(Integer endDateMonth) {
		this.endDateMonth = endDateMonth;
		setupEndDate();
	}
	
	public void setEndDateYear(Integer endDateYear) {
		this.endDateYear = endDateYear;
		setupEndDate();
	}

	private void setupEndDate() {
		if (endDateYear != null && endDateMonth != null) {
			setEndDate(LocalDate.of(endDateYear, endDateMonth, 1));
		} else {
			setEndDate(null);
		}
		
	}
}
