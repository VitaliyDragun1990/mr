package com.revenat.myresume.presentation.image.model;

import java.io.Serializable;

import com.revenat.myresume.infrastructure.util.CommonUtils;

/**
 * Represents image uploaded by the user.
 * 
 * @author Vitaliy Dragun
 *
 */
public class UploadedImageResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String largeUrl;
	private final String smallUrl;

	public UploadedImageResult(String largeUrl, String smallUrl) {
		this.largeUrl = largeUrl;
		this.smallUrl = smallUrl;
	}

	public String getLargeUrl() {
		return largeUrl;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}

}
