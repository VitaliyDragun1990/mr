package com.revenat.myresume.presentation.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.service.profile.CreateProfileService;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

@Service
class SignUpServiceImpl implements SignUpService {
	private final CreateProfileService profileService;
	private final ProfileRepository profileRepo;


	@Autowired
	public SignUpServiceImpl(ProfileRepository profileRepo, CreateProfileService profileService) {
		this.profileRepo = profileRepo;
		this.profileService = profileService;
	}

	@Override
	public AuthenticatedUser signUp(String firstName, String lastName, String password) {
		Profile profile = new Profile();
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setPassword(password);
		return signUp(profile);
	}

	@Override
	public AuthenticatedUser signUp(Profile newProfile) {
		String profileId = profileService.createProfile(new ProfileDTO(newProfile));
		Profile createdProfile = profileRepo.findOne(profileId);

		return (AuthenticatedUser) SecurityUtil.authenticate(createdProfile).getPrincipal();
	}

}
