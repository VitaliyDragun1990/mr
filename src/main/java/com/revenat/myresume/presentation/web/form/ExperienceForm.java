package com.revenat.myresume.presentation.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.application.validation.annotation.FirstFieldLessThanSecond;
import com.revenat.myresume.presentation.web.form.annotation.EnableFormErrorConvertation;

@EnableFormErrorConvertation(
		formName = "experienceForm", fieldReference = "endDate", validationAnnotationClasses = FirstFieldLessThanSecond.class
		)
public class ExperienceForm extends AbstractForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Valid
	private List<PracticalExperienceDTO> items = new ArrayList<>();
	
	public ExperienceForm() {
	}

	public ExperienceForm(List<PracticalExperienceDTO> items) {
		this.items = items;
	}

	public List<PracticalExperienceDTO> getItems() {
		return items;
	}

	public void setItems(List<PracticalExperienceDTO> items) {
		this.items = items;
	}
}
