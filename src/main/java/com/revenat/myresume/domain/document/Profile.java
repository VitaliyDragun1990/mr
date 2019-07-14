package com.revenat.myresume.domain.document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revenat.myresume.domain.annotation.OptionalInfoField;
import com.revenat.myresume.domain.annotation.RequiredInfoField;


@Document(indexName = "my_resume", type = "profile")
@org.springframework.data.mongodb.core.mapping.Document(collection = "profile")
public class Profile extends AbstractDocument<String> implements Serializable {
	private static final long serialVersionUID = 8326456967466271180L;
	
	@Id
	private String id;

	@Indexed(unique = true)
	private String uid;

	private String firstName;

	private String lastName;

	@RequiredInfoField
	private LocalDate birthDay;

	@JsonIgnore
	@RequiredInfoField
	@Indexed(unique = true)
	private String phone;

	@JsonIgnore
	@RequiredInfoField
	@Indexed(unique = true)
	private String email;

	@RequiredInfoField
	private String country;

	@RequiredInfoField
	private String city;

	@RequiredInfoField
	private String objective;
	
	@RequiredInfoField
	private String summary;

	@JsonIgnore
	private String largePhoto;

	private String smallPhoto;

	@OptionalInfoField
	private String info;

	@JsonIgnore
	private String password;

	@JsonIgnore
	@Indexed
	private boolean completed;

	@JsonIgnore
	@Indexed
	@CreatedDate
	private LocalDateTime created;

	@JsonIgnore
	private Contacts contacts;
	
	private List<Certificate> certificates = new ArrayList<>();
	
	private List<Course> courses = new ArrayList<>();
	
	@JsonIgnore
	private List<Education> educations = new ArrayList<>();
	
	private List<PracticalExperience> experience = new ArrayList<>();
	
	private List<Language> languages = new ArrayList<>();
	
	private List<Skill> skills = new ArrayList<>();
	
	@JsonIgnore
	private List<Hobby> hobbies = new ArrayList<>();
	
	public void addCertificate(Certificate certificate) {
		certificates.add(certificate);
	}
	
	public void updateCertificates(List<Certificate> certificates) {
		this.certificates.clear();
		this.certificates.addAll(certificates);
	}
	
	public void addCourse(Course course) {
		courses.add(course);
	}
	
	public void updateCourses(List<Course> courses) {
		this.courses.clear();
		this.courses.addAll(courses);
	}
	
	public void addEducation(Education education) {
		educations.add(education);
	}
	
	public void updateEducation(List<Education> educations) {
		this.educations.clear();
		this.educations.addAll(educations);
	}
	
	public void addExerience(PracticalExperience experience) {
		this.experience.add(experience);
	}
	
	public void updateExperience(List<PracticalExperience> experience) {
		this.experience.clear();
		this.experience.addAll(experience);
	}
	
	public void addLanguage(Language language) {
		languages.add(language);
	}
	
	public void updateLanguages(List<Language> languages) {
		this.languages.clear();
		this.languages.addAll(languages);
	}
	
	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	public void updateSkills(List<Skill> skills) {
		this.skills.clear();
		this.skills.addAll(skills);
	}
	
	public void addHobby(Hobby hobby) {
		hobbies.add(hobby);
	}
	
	public void updateHobbies(List<Hobby> hobbies) {
		this.hobbies.clear();
		this.hobbies.addAll(hobbies);
	}

	@Override
	public String getId() {
		return id;
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

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLargePhoto() {
		return largePhoto;
	}

	public void setLargePhoto(String largePhoto) {
		this.largePhoto = largePhoto;
	}

	public String getSmallPhoto() {
		return smallPhoto;
	}

	public void setSmallPhoto(String smallPhoto) {
		this.smallPhoto = smallPhoto;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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

	public Contacts getContacts() {
		if (contacts == null) {
			contacts = new Contacts();
		}
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public List<PracticalExperience> getExperience() {
		return experience;
	}

	public void setExperience(List<PracticalExperience> experience) {
		this.experience = experience;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Hobby> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<Hobby> hobbies) {
		this.hobbies = hobbies;
	}
	
}
