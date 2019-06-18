package com.revenat.myresume.presentation.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.revenat.myresume.application.dto.SkillDTO;

public class SkillForm extends AbstractForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Valid
	private List<SkillDTO> items = new ArrayList<>();
	
	public SkillForm() {
	}

	public SkillForm(List<SkillDTO> items) {
		this.items = items;
	}

	public List<SkillDTO> getItems() {
		return items;
	}

	public void setItems(List<SkillDTO> items) {
		this.items = items;
	}
	
}
