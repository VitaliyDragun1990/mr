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

	private final String largeImageLink;
	private final String smallImageLink;

	public UploadedImageResult(String largeUrl, String smallUrl) {
		this.largeImageLink = largeUrl;
		this.smallImageLink = smallUrl;
	}

	public String getLargeImageLink() {
		return largeImageLink;
	}

	public String getSmallImageLink() {
		return smallImageLink;
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}

}
