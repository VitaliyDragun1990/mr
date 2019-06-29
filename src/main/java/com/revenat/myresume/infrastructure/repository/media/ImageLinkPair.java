package com.revenat.myresume.infrastructure.repository.media;

import com.revenat.myresume.infrastructure.util.CommonUtils;

/**
 * Convenient container that holds path to saved image in two variations: large
 * and small size.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ImageLinkPair {

	private final String largeImageLink;
	private final String smallImageLink;

	public ImageLinkPair(String largeImagePath, String smallImagePath) {
		this.largeImageLink = largeImagePath;
		this.smallImageLink = smallImagePath;
	}

	public String getLargeImageLink() {
		return largeImageLink;
	}

	public String getSmallImageLink() {
		return smallImageLink;
	}
	
	public String[] getImageLinks() {
		return new String[] {smallImageLink, largeImageLink};
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}

}
