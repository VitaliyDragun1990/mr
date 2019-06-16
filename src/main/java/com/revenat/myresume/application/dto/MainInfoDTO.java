package com.revenat.myresume.application.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.util.CommonUtil;

public class MainInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String largePhoto;
	private String smallPhoto;
	private LocalDate birthDate;
	private String country;
	private String city;
	private String email;
	private String phone;
	private String objective;
	private String summary;
	
	public MainInfoDTO() {
	}
	
	public MainInfoDTO(Profile profile) {
		if (profile != null) {
			setBirthDate(profile.getBirthDay());
			setCity(profile.getCity());
			setCountry(profile.getCountry());
			setEmail(profile.getEmail());
			setLargePhoto(profile.getLargePhoto());
			setObjective(profile.getObjective());
			setPhone(profile.getPhone());
			setSmallPhoto(profile.getSmallPhoto());
			setSummary(profile.getSummary());
		}
	}
	
	public int getAge() {
		return (int) ChronoUnit.YEARS.between(getBirthDate(), LocalDate.now());
	}
	
	public String getProfilePhoto() {
		if (largePhoto != null) {
			return largePhoto;
		} else {
			return "/static/img/profile-placeholder.png";
		}
	}
	
	public String updateProfilePhotos(String largePhoto, String smallPhoto) {
		String oldLargePhoto = this.largePhoto;
		setLargePhoto(largePhoto);
		setSmallPhoto(smallPhoto);
		return oldLargePhoto;
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	
	@Override
	public String toString() {
		return CommonUtil.toString(this);
	}

}
