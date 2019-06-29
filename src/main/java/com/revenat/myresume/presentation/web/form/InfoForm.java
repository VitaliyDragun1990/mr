package com.revenat.myresume.presentation.web.form;

import org.hibernate.validator.constraints.SafeHtml;

import com.revenat.myresume.application.validation.annotation.EnglishLanguage;

public class InfoForm extends AbstractForm {
	private static final long serialVersionUID = 1L;

	@EnglishLanguage
	@SafeHtml
	private String info;
	
	public InfoForm() {
	}

	public InfoForm(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
