package com.revenat.myresume.presentation.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.transformer.Transformer;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;

@Service
class CustomUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	private final ProfileRepository profileRepo;
	private final Transformer transformer;

	@Autowired
	public CustomUserDetailsService(ProfileRepository profileRepo, Transformer transformer) {
		this.profileRepo = profileRepo;
		this.transformer = transformer;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Profile> profile = findProfile(username);
		if (profile.isPresent()) {
			return new AuthenticatedUser(transformer.transform(profile.get(), ProfileDTO.class));
		} else {
			LOGGER.error("Profile not found by {}", username);
			throw new UsernameNotFoundException("Profile not found by " + username);
		}
	}
	
	private Optional<Profile> findProfile(String anyUniqueId) {
		Optional<Profile> profile = profileRepo.findOneByUid(anyUniqueId);
		if (!profile.isPresent()) {
			profile = profileRepo.findByEmail(anyUniqueId);
			if (!profile.isPresent()) {
				profile = profileRepo.findByPhone(anyUniqueId);
			}
		}
		return profile;
	}

}
