package com.revenat.myresume.application.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.revenat.myresume.infrastructure.util.CommonUtils;

public class ProfileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String uid;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private boolean completed;
	
	private LocalDateTime created;
	
	private String info;

	@Valid
	private MainInfoDTO mainInfo;
	
	@Valid
	private ContactsDTO contacts;
	
	@Valid
	private List<CertificateDTO> certificates = new ArrayList<>();
	
	@Valid
	private List<CourseDTO> courses = new ArrayList<>();
	
	@Valid
	private List<EducationDTO> educations = new ArrayList<>();
	
	@Valid
	private List<ExperienceDTO> experience = new ArrayList<>();
	
	@Valid
	private List<LanguageDTO> languages = new ArrayList<>();
	
	@Valid
	private List<SkillDTO> skills = new ArrayList<>();
	
	@Valid
	private List<HobbyDTO> hobbies = new ArrayList<>();

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public int getAge() {
		return mainInfo.getAge();
	}

	public String getProfilePhoto() {
		return mainInfo.getProfilePhoto();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public MainInfoDTO getMainInfo() {
		return mainInfo;
	}

	public void setMainInfo(MainInfoDTO mainInfo) {
		this.mainInfo = mainInfo;
	}

	public ContactsDTO getContacts() {
		return contacts;
	}

	public void setContacts(ContactsDTO contacts) {
		this.contacts = contacts;
	}

	public List<CertificateDTO> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<CertificateDTO> certificates) {
		this.certificates = certificates;
	}

	public List<CourseDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDTO> courses) {
		this.courses = courses;
	}

	public List<EducationDTO> getEducations() {
		return educations;
	}

	public void setEducations(List<EducationDTO> educations) {
		this.educations = educations;
	}

	public List<ExperienceDTO> getExperience() {
		return experience;
	}

	public void setExperience(List<ExperienceDTO> experience) {
		this.experience = experience;
	}

	public List<LanguageDTO> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguageDTO> languages) {
		this.languages = languages;
	}

	public List<SkillDTO> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillDTO> skills) {
		this.skills = skills;
	}

	public List<HobbyDTO> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<HobbyDTO> hobbies) {
		this.hobbies = hobbies;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
