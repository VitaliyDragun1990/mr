package com.revenat.myresume.presentation.security.model;

import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.presentation.config.Constants;

public final class AuthenticatedUser extends User {
	private static final long serialVersionUID = 1L;

	private final Long id;
	private final String fullName;
	private final boolean isRegistrationCompleted;
	
	public AuthenticatedUser(Profile profile) {
		super(profile.getUid(), profile.getPassword(), true, true, true, true,
				Collections.singleton(new SimpleGrantedAuthority(Constants.USER)));
		this.id = profile.getId();
		this.fullName = profile.getFirstName() + " " + profile.getLastName();
		this.isRegistrationCompleted = profile.isCompleted();
	}

	public Long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}
	
	public boolean isRegistrationCompleted() {
		return isRegistrationCompleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticatedUser other = (AuthenticatedUser) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return String.format("AuthenticatedUser [id=%s, username=%s]", id, getUsername());
	}
}
