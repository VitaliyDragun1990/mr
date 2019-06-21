package com.revenat.myresume.presentation.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.revenat.myresume.application.dto.CourseDTO;

public class CourseForm extends AbstractForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Valid
	private List<CourseDTO> items = new ArrayList<>();
	
	public CourseForm() {
	}

	public CourseForm(List<CourseDTO> items) {
		this.items = items;
	}

	public List<CourseDTO> getItems() {
		return items;
	}

	public void setItems(List<CourseDTO> items) {
		this.items = items;
	}
}
