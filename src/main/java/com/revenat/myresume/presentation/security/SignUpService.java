package com.revenat.myresume.presentation.security;

import com.revenat.myresume.domain.entity.Profile;

public interface SignUpService {

	AuthenticatedUser signUp(String firstName, String lastName, String password);
	
	AuthenticatedUser signUp(Profile newProfile);
}
