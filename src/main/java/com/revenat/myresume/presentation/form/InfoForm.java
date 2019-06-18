package com.revenat.myresume.presentation.form;

import com.revenat.myresume.application.validation.annotation.EnglishLanguage;

public class InfoForm extends AbstractForm {
	private static final long serialVersionUID = 1L;

	@EnglishLanguage
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
