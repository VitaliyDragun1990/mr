package com.revenat.myresume.application.dto;

import java.io.Serializable;

import com.revenat.myresume.infrastructure.util.CommonUtils;

public class ProfileRestoreDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
