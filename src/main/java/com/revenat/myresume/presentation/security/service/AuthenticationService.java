package com.revenat.myresume.presentation.security.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

/**
 * Responsible for authenticating users by some sort of unique identifier.
 * 
 * @author Vitaliy Dragun
 *
 */
@Service
class AuthenticationService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

	private final ProfileRepository profileRepo;

	@Autowired
	public AuthenticationService(ProfileRepository profileRepo) {
		this.profileRepo = profileRepo;
	}

	@Override
	public AuthenticatedUser loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Profile> profile = findProfile(username);
		if (profile.isPresent()) {
			return new AuthenticatedUser(profile.get());
		} else {
			LOGGER.error("Profile not found by {}", username);
			throw new UsernameNotFoundException("Profile not found by " + username);
		}
	}

	private Optional<Profile> findProfile(String anyUniqueId) {
		return profileRepo.findByUidOrEmailOrPhone(anyUniqueId, anyUniqueId, anyUniqueId);
	}

}
