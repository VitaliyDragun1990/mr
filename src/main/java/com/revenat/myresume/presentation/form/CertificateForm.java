package com.revenat.myresume.presentation.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.revenat.myresume.application.dto.CertificateDTO;

public class CertificateForm extends AbstractForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Valid
	private List<CertificateDTO> items = new ArrayList<>();
	
	public CertificateForm() {
	}

	public CertificateForm(List<CertificateDTO> items) {
		this.items = items;
	}

	public List<CertificateDTO> getItems() {
		return items;
	}

	public void setItems(List<CertificateDTO> items) {
		this.items = items;
	}
}
