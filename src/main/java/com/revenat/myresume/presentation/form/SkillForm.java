package com.revenat.myresume.presentation.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.infrastructure.util.CommonUtil;

public class SkillForm implements Serializable {
	private static final long serialVersionUID = 1L;

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
	
	@Override
	public String toString() {
		return CommonUtil.toString(this);
	}
	
}
