package com.revenat.myresume.application.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.revenat.myresume.domain.entity.Contacts;
import com.revenat.myresume.infrastructure.util.CommonUtils;

public class ContactsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@URL
	@Size(max = 80)
	private String skype;
	@URL
	@Size(max = 255)
	private String vkontakte;
	@URL
	@Size(max = 255)
	private String facebook;
	@URL
	@Size(max = 255)
	private String linkedin;
	@URL
	@Size(max = 255)
	private String github;
	@URL
	@Size(max = 255)
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
	public String toString() {
		return CommonUtils.toString(this);
	}

}
