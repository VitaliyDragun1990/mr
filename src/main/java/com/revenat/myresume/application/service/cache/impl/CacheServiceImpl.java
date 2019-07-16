package com.revenat.myresume.application.service.cache.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.util.Checks;

@Service
class CacheServiceImpl implements CacheService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);
	
	private final ProfileRepository profileRepo;

	@Autowired
	public CacheServiceImpl(ProfileRepository profileRepo) {
		this.profileRepo = profileRepo;
	}

	@Override
	@Cacheable(cacheNames = "profile")
	public Optional<Profile> findProfileByUid(String uid) {
		Checks.checkParam(uid != null, "uid of the profile to find can not be null");
		
		try {
			LOGGER.debug("Profile not found in cache by uid={}, loading profile from repository ", uid);
			return profileRepo.findOneByUid(uid);
		} finally {
			LOGGER.debug("Profile added successfully to cache by uid={}", uid);
		}
	}

	@Override
	@CacheEvict(cacheNames = "profile")
	public void deleteProfileByUid(String uid) {
		Checks.checkParam(uid != null, "uid of the profile to delete can not be null");
		
		LOGGER.debug("Profile removed from cache by uid={}", uid);
	}

}
