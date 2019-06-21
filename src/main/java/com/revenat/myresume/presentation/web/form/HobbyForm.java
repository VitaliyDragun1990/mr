package com.revenat.myresume.presentation.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.revenat.myresume.application.dto.HobbyDTO;

public class HobbyForm extends AbstractForm {
	private static final long serialVersionUID = 1L;

	@Valid
	private List<HobbyDTO> items = new ArrayList<>();
	
	public HobbyForm() {
	}

	public HobbyForm(List<HobbyDTO> items) {
		this.items = items;
	}

	public List<HobbyDTO> getItems() {
		return items;
	}

	public void setItems(List<HobbyDTO> items) {
		this.items = items;
	}
	
}
