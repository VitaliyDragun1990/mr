package com.revenat.myresume.domain.document;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revenat.myresume.infrastructure.util.CommonUtils;


public class Certificate implements Serializable, ProfileDocument {
	private static final long serialVersionUID = -8979436302735566963L;
	
	private String name;
	
	@JsonIgnore
	private String largeUrl;
	
	@JsonIgnore
	private String smallUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLargeUrl() {
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
	
}
