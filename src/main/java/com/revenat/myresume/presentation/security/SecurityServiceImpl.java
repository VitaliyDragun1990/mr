package com.revenat.myresume.presentation.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revenat.myresume.application.service.profile.EditProfileService;
import com.revenat.myresume.application.service.profile.SearchProfileService;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;

@Service
class SecurityServiceImpl implements SecurityService {
	private final SearchProfileService searchProfileService;
	private final EditProfileService editProfileService;
	private final ProfileRepository profileRepo;
	
	@Autowired
	public SecurityServiceImpl(SearchProfileService searchProfileService, EditProfileService editProfileService,
			ProfileRepository profileRepo) {
		this.searchProfileService = searchProfileService;
		this.editProfileService = editProfileService;
		this.profileRepo = profileRepo;
	}

	@Override
	@Transactional
	public void completeRegistration(AuthenticatedUser authenticatedUser) {
		Profile profile = profileRepo.findOne(authenticatedUser.getId());
		profile.setCompleted(true);
		profileRepo.save(profile);
		SecurityUtil.authenticate(profile);
	}

	@Override
	public Optional<AuthenticatedUser> findByEmail(String email) {
		Optional<Profile> optional = profileRepo.findByEmail(email);
		if (optional.isPresent()) {
			return Optional.of(SecurityUtil.authenticate(optional.get()));
		}
		return Optional.empty();
	}

	@Override
	public String resetPassword(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePassword(String email, String token, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean verifyPasswordResetToken(String email, String token) {
		// TODO Auto-generated method stub
		return false;
	}

}
