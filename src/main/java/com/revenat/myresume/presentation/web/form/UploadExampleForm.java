package com.revenat.myresume.presentation.web.form;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.revenat.myresume.infrastructure.util.CommonUtils;

public class UploadExampleForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private MultipartFile file;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
