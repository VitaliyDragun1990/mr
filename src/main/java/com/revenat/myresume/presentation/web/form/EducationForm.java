package com.revenat.myresume.presentation.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.revenat.myresume.application.dto.EducationDTO;

public class EducationForm extends AbstractForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Valid
	private List<EducationDTO> items = new ArrayList<>();
	
	public EducationForm() {
	}

	public EducationForm(List<EducationDTO> items) {
		this.items = items;
	}

	public List<EducationDTO> getItems() {
		return items;
	}

	public void setItems(List<EducationDTO> items) {
		this.items = items;
	}
}
