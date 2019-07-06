package com.revenat.myresume.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revenat.myresume.domain.annotation.OptionalInfoField;
import com.revenat.myresume.domain.annotation.RequiredInfoField;

@Entity
@Table(name = "profile")
@Document(indexName = "my_resume", type = "profile")
public class Profile extends AbstractEntity<Long> {
	private static final long serialVersionUID = 8326456967466271180L;

	@Id
	@SequenceGenerator(name = "profile_seq_generator", sequenceName = "profile_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq_generator")
	private Long id;

	@Column(length = 100, unique = true, nullable = false)
	private String uid;

	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@Column(name = "birth_day")
	@RequiredInfoField
	private LocalDate birthDay;

	@Column(length = 20, unique = true)
	@JsonIgnore
	@RequiredInfoField
	private String phone;

	@Column(length = 100, unique = true)
	@JsonIgnore
	@RequiredInfoField
	private String email;

	@Column(length = 60)
	@RequiredInfoField
	private String country;

	@Column(length = 100)
	@RequiredInfoField
	private String city;

	@Column
	@RequiredInfoField
	private String objective;
	
	@Column
	@RequiredInfoField
	private String summary;

	@Column(name = "large_photo")
	@JsonIgnore
	private String largePhoto;

	@Column(name = "small_photo")
	private String smallPhoto;

	@Column
	@OptionalInfoField
	private String info;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@Column(nullable = false)
	@JsonIgnore
	private boolean completed;

	@Column(nullable = false, updatable = false)
	@JsonIgnore
	private LocalDateTime created;

	@Embedded
	@JsonIgnore
	private Contacts contacts;
	
	@OneToMany(
			mappedBy = "profile",
			/*
			 * cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true,
			 */
			fetch = FetchType.LAZY
			)
	private List<Certificate> certificates = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "profile",
			/*
			 * cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true,
			 */
			fetch = FetchType.LAZY
			)
	@OrderBy("id ASC"/* "endDate DESC" */)
	private List<Course> courses = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "profile",
			/*
			 * cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true,
			 */
			fetch = FetchType.LAZY
			)
	@OrderBy("id ASC"/* "endYear DESC, startYear DESC, id DESC" */)
	@JsonIgnore
	private List<Education> educations = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "profile",
			/*
			 * cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true,
			 */
			fetch = FetchType.LAZY
			)
	@OrderBy("id ASC"/* "endDate DESC" */)
	private List<PracticalExperience> experience = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "profile",
			/*
			 * cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true,
			 */
			fetch = FetchType.LAZY
			)
	@OrderBy("id ASC")
	private List<Language> languages = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "profile",
			/*
			 * cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true,
			 */
			fetch = FetchType.LAZY
			)
	@OrderBy("id ASC"/* "category ASC" */)
	private List<Skill> skills = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "profile",
			/*
			 * cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true,
			 */
			fetch = FetchType.LAZY
			)
	@OrderBy("id ASC"/* "name ASC" */)
	@JsonIgnore
	private List<Hobby> hobbies = new ArrayList<>();
	
	@PrePersist
	public void prePersist() {
		if (getId() == 0) {
			setCreated(LocalDateTime.now());
		}
	}
	
	public void addCertificate(Certificate certificate) {
		certificates.add(certificate);
		certificate.setProfile(this);
	}
	
	public void updateCertificates(List<Certificate> certificates) {
		this.certificates.clear();
		this.certificates.addAll(certificates);
		this.certificates.forEach(c -> c.setProfile(this));
	}
	
	public void addCourse(Course course) {
		courses.add(course);
		course.setProfile(this);
	}
	
	public void updateCourses(List<Course> courses) {
		this.courses.clear();
		this.courses.addAll(courses);
		this.courses.forEach(c -> c.setProfile(this));
	}
	
	public void addEducation(Education education) {
		educations.add(education);
		education.setProfile(this);
	}
	
	public void updateEducation(List<Education> educations) {
		this.educations.clear();
		this.educations.addAll(educations);
		this.educations.forEach(e -> e.setProfile(this));
	}
	
	public void addExerience(PracticalExperience experience) {
		this.experience.add(experience);
		experience.setProfile(this);
	}
	
	public void updateExperience(List<PracticalExperience> experience) {
		this.experience.clear();
		this.experience.addAll(experience);
		this.experience.forEach(exp -> exp.setProfile(this));
	}
	
	public void addLanguage(Language language) {
		languages.add(language);
		language.setProfile(this);
	}
	
	public void updateLanguages(List<Language> languages) {
		this.languages.clear();
		this.languages.addAll(languages);
		this.languages.forEach(l -> l.setProfile(this));
	}
	
	public void addSkill(Skill skill) {
		skills.add(skill);
		skill.setProfile(this);
	}
	
	public void updateSkills(List<Skill> skills) {
		this.skills.clear();
		this.skills.addAll(skills);
		this.skills.forEach(skill -> skill.setProfile(this));
	}
	
	public void addHobby(Hobby hobby) {
		hobbies.add(hobby);
		hobby.setProfile(this);
	}
	
	public void updateHobbies(List<Hobby> hobbies) {
		this.hobbies.clear();
		this.hobbies.addAll(hobbies);
		this.hobbies.forEach(h -> h.setProfile(this));
	}

	@Override
	public Long getId() {
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

	public void setId(Long id) {
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
