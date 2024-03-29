package com.revenat.myresume.application.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import com.revenat.myresume.application.validation.annotation.Adulthood;
import com.revenat.myresume.application.validation.annotation.EnglishLanguage;
import com.revenat.myresume.application.validation.annotation.Phone;
import com.revenat.myresume.domain.annotation.RequiredInfoField;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.util.CommonUtils;

public class MainInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Size(max = 255)
	private String largePhoto;
	
	@Size(max = 255)
	private String smallPhoto;
	
	@NotNull
	@Adulthood
	@RequiredInfoField
	private LocalDate birthDay;
	
	@NotBlank
	@Size(max = 60)
	@SafeHtml
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	@RequiredInfoField
	private String country;
	
	@NotBlank
	@Size(max = 100)
	@SafeHtml
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	@RequiredInfoField
	private String city;
	
	@NotBlank
	@Email
	@Size(max = 100)
	@EnglishLanguage
	@RequiredInfoField
	private String email;
	
	@NotBlank
	@Size(max = 20)
	@Phone
	@RequiredInfoField
	private String phone;
	
	@NotBlank
	@SafeHtml
	@EnglishLanguage
	@RequiredInfoField
	private String objective;
	
	@NotBlank
	@EnglishLanguage
	@RequiredInfoField
	private String summary;
	
	public MainInfoDTO() {
	}
	
	public MainInfoDTO(Profile profile) {
		if (profile != null) {
			setBirthDay(profile.getBirthDay());
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
	
	public boolean isCompleted() {
		boolean hasPhoto = CommonUtils.isNotBlank(getLargePhoto()) && CommonUtils.isNotBlank(getSmallPhoto());
		boolean hasAddress = CommonUtils.isNotBlank(getCountry()) && CommonUtils.isNotBlank(getCity());
		boolean hasBirthday = getBirthDay() != null;
		boolean hasPhoneAndEmail = CommonUtils.isNotBlank(getPhone()) && CommonUtils.isNotBlank(getEmail());
		boolean hasInfo = CommonUtils.isNotBlank(getObjective()) && CommonUtils.isNotBlank(getSummary());
		return hasPhoto && hasAddress && hasBirthday && hasPhoneAndEmail && hasInfo;
	}
	
	public int getAge() {
		return (int) ChronoUnit.YEARS.between(getBirthDay(), LocalDate.now());
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

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDate) {
		this.birthDay = birthDate;
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
		return CommonUtils.toString(this);
	}

}
