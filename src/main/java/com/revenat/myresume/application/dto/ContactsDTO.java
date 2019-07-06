package com.revenat.myresume.application.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import com.revenat.myresume.application.validation.annotation.EnglishLanguage;
import com.revenat.myresume.domain.entity.Contacts;
import com.revenat.myresume.infrastructure.util.CommonUtils;

public class ContactsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@SafeHtml
	@Size(max = 80)
	@EnglishLanguage
	private String skype;
	
	@URL(host = "vk.com")
	@Size(max = 255)
	@EnglishLanguage
	private String vkontakte;
	
	@URL(host = "facebook.com")
	@Size(max = 255)
	@EnglishLanguage
	private String facebook;
	
	@URL(host = "linkedin.com")
	@Size(max = 255)
	@EnglishLanguage
	private String linkedin;
	
	@URL(host = "github.com")
	@Size(max = 255)
	@EnglishLanguage
	private String github;
	
	@URL(host = "stackoverflow.com")
	@Size(max = 255)
	@EnglishLanguage
	private String stackoverflow;
	
	public ContactsDTO() {
	}
	
	public ContactsDTO(Contacts entity) {
		if (entity != null) {
			this.skype = entity.getSkype();
			this.vkontakte = entity.getVkontakte();
			this.facebook = entity.getFacebook();
			this.linkedin = entity.getLinkedin();
			this.github = entity.getGithub();
			this.stackoverflow = entity.getStackoverflow();
		}
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getVkontakte() {
		return vkontakte;
	}

	public void setVkontakte(String vkontakte) {
		this.vkontakte = vkontakte;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getStackoverflow() {
		return stackoverflow;
	}

	public void setStackoverflow(String stackoverflow) {
		this.stackoverflow = stackoverflow;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(facebook, github, linkedin, skype, stackoverflow, vkontakte);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactsDTO other = (ContactsDTO) obj;
		return Objects.equals(facebook, other.facebook) && Objects.equals(github, other.github)
				&& Objects.equals(linkedin, other.linkedin) && Objects.equals(skype, other.skype)
				&& Objects.equals(stackoverflow, other.stackoverflow) && Objects.equals(vkontakte, other.vkontakte);
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}

}
