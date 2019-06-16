package com.revenat.myresume.application.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.revenat.myresume.domain.entity.Experience;
import com.revenat.myresume.infrastructure.util.CommonUtil;

public class ExperienceDTO extends AbstractEndDateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private String position;
	private String company;
	private LocalDate startDate;
	private String responsibilities;
	private String demo;
	private String sourceCode;
	
	private Integer startDateMonth;
	private Integer startDateYear;
	
	public ExperienceDTO() {
	}

	public ExperienceDTO(Experience entity, Long profileId) {
		this.id = entity.getId();
		this.profileId = profileId;
		this.position = entity.getPosition();
		this.company = entity.getCompany();
		this.startDate = entity.getStartDate();
		this.setEndDate(entity.getEndDate());
		this.responsibilities = entity.getResponsibilities();
		this.demo = entity.getDemo();
		this.sourceCode = entity.getSourceCode();
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
	
	public Integer getStartDateMonth() {
		if (startDate != null) {
			return startDate.getMonthValue();
		} else {
			return null;
		}
	}
	
	public Integer getStartDateYear() {
		if (startDate != null) {
			return startDate.getYear();
		} else {
			return null;
		}
	}
	
	public void setStartDateMonth(Integer startDateMonth) {
		this.startDateMonth = startDateMonth;
		setupStartDate();
	}
	
	public void setStartDateYear(Integer startDateYear) {
		this.startDateYear = startDateYear;
		setupStartDate();
	}
	
	private void setupStartDate() {
		if (startDateYear != null && startDateMonth != null) {
			setStartDate(LocalDate.of(startDateYear, startDateMonth, 1));
		} else {
			setStartDate(null);
		}
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((demo == null) ? 0 : demo.hashCode());
		result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((responsibilities == null) ? 0 : responsibilities.hashCode());
		result = prime * result + ((sourceCode == null) ? 0 : sourceCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ExperienceDTO))
			return false;
		ExperienceDTO other = (ExperienceDTO) obj;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (demo == null) {
			if (other.demo != null)
				return false;
		} else if (!demo.equals(other.demo))
			return false;
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
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (responsibilities == null) {
			if (other.responsibilities != null)
				return false;
		} else if (!responsibilities.equals(other.responsibilities))
			return false;
		if (sourceCode == null) {
			if (other.sourceCode != null)
				return false;
		} else if (!sourceCode.equals(other.sourceCode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return CommonUtil.toString(this);
	}

}
