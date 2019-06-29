package com.revenat.myresume.presentation.image.model;

import java.io.Serializable;

/**
 * Represents certificate uploaded by the user
 * 
 * @author Vitaliy Dragun
 *
 */
public class UploadedCertificateResult extends UploadedImageResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	public UploadedCertificateResult(String largeUrl, String smallUrl, String name) {
		super(largeUrl, smallUrl);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
