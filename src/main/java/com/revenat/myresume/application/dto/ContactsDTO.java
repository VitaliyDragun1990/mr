package com.revenat.myresume.application.dto;

import java.io.Serializable;

import com.revenat.myresume.domain.entity.Contacts;
import com.revenat.myresume.infrastructure.util.CommonUtil;

public class ContactsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String skype;
	private String vkontakte;
	private String facebook;
	private String linkedin;
	private String github;
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
		return CommonUtil.toString(this);
	}

}
