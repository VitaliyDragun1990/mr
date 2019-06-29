package com.revenat.myresume.infrastructure.repository.media;

public enum ImageType {
	AVATARS(110, 110, 400, 400),

	CERTIFICATES(142, 100, 900, 400);

	private final int smallWidth;
	private final int smallHeight;
	private final int largeWidth;
	private final int largeHeight;

	private ImageType(int smallWidth, int smallHeight, int largeWidth, int largeHeight) {
		this.smallWidth = smallWidth;
		this.smallHeight = smallHeight;
		this.largeWidth = largeWidth;
		this.largeHeight = largeHeight;
	}
	
	public String getFolderName() {
		return name().toLowerCase();
	}

	public int getSmallWidth() {
		return smallWidth;
	}

	public int getSmallHeight() {
		return smallHeight;
	}

	public int getLargeWidth() {
		return largeWidth;
	}

	public int getLargeHeight() {
		return largeHeight;
	}
}