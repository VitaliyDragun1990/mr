package com.revenat.myresume.infrastructure.repository.media;

/**
 * Convenient container that holds path to saved image in two variations:
 * original(large) and small size.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ImagePair {

	private final String largeImagePath;
	private final String smallImagePath;

	public ImagePair(String largeImagePath, String smallImagePath) {
		this.largeImagePath = largeImagePath;
		this.smallImagePath = smallImagePath;
	}

	public String getLargeImagePath() {
		return largeImagePath;
	}

	public String getSmallImagePath() {
		return smallImagePath;
	}

}
