package com.revenat.myresume.presentation.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.service.profile.CreateProfileService;
import com.revenat.myresume.domain.entity.Profile;
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
	@Transactional
	public AuthenticatedUser signUp(String firstName, String lastName, String password) {
		Profile profile = new Profile();
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setPassword(password);
		return signUp(profile);
	}

	@Override
	@Transactional
	public AuthenticatedUser signUp(Profile newProfile) {
		Long profileId = profileService.createProfile(new ProfileDTO(newProfile));
		Profile createdProfile = profileRepo.findOne(profileId);

		return (AuthenticatedUser) SecurityUtil.authenticate(createdProfile).getPrincipal();
	}

}
