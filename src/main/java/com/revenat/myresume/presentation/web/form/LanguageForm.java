package com.revenat.myresume.presentation.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.revenat.myresume.application.dto.LanguageDTO;

public class LanguageForm extends AbstractForm {
	private static final long serialVersionUID = 1L;

	@Valid
	private List<LanguageDTO> items = new ArrayList<>();
	
	public LanguageForm() {
	}

	public LanguageForm(List<LanguageDTO> items) {
		this.items = items;
	}

	public List<LanguageDTO> getItems() {
		return items;
	}

	public void setItems(List<LanguageDTO> items) {
		this.items = items;
	}
	
}
