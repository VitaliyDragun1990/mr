package com.revenat.myresume.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "education")
public class Education extends AbstractEntity<Long> implements ProfileEntity {
	private static final long serialVersionUID = -5164467264416703233L;

	@Id
	@SequenceGenerator(name = "education_seq_generator", sequenceName = "education_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_seq_generator")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;
	
	@Column(length = 100, nullable = false)
	private String summary;
	
	@Column(name = "start_year", nullable = false)
	private Integer startYear;
	
	@Column(name = "end_year")
	private Integer endYear;
	
	@Column(nullable = false)
	private String university;
	
	@Column(nullable = false)
	private String faculty;
	
	@Override
	public Long getId() {
		return id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
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

	public void setId(Long id) {
		this.id = id;
	}
	
}
