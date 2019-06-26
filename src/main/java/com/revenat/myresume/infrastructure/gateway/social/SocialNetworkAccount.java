package com.revenat.myresume.infrastructure.gateway.social;

import com.revenat.myresume.infrastructure.util.CommonUtils;

/**
 * This component represents abstraction over user's account from some kind of
 * social network.
 * 
 * @author Vitaly Dragun
 *
 */
public class SocialNetworkAccount {
	private final String firstName;
	private final String lastName;
	private final String avatarUrl;
	private final String email;

	public SocialNetworkAccount(String firstName, String lastName, String avatarUrl, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.avatarUrl = avatarUrl;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
