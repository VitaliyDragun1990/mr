package com.revenat.myresume.domain.document;

import java.io.Serializable;

import com.revenat.myresume.infrastructure.util.CommonUtils;

public class Hobby implements ProfileDocument, Serializable {
	private static final long serialVersionUID = 5361190629069199649L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
