package com.revenat.myresume.domain.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profileRestore")
public class ProfileRestore extends AbstractDocument<String> implements Serializable {
	private static final long serialVersionUID = -3451745600693510994L;

	@Id
	private String id;

	private String token;
	
	@DBRef
	private Profile profile;
	
	@Override
	public String getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
