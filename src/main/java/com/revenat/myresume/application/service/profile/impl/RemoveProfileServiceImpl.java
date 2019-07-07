package com.revenat.myresume.application.service.profile.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.service.profile.RemoveProfileService;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;

@Service
class RemoveProfileServiceImpl extends AbstractModifyProfileService implements RemoveProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveProfileServiceImpl.class);

	@Autowired
	public RemoveProfileServiceImpl(ProfileRepository profileRepo, ImageStorageService imageStorageService,
			SearchIndexingService searchIndexingService, CacheService cacheService) {
		super(profileRepo, imageStorageService, searchIndexingService, cacheService);
	}

	@Override
	public void removeProfile(long profileId) {
		Optional<Profile> optional = profileRepo.findOneById(profileId);
		if (optional.isPresent()) {
			Profile profile = optional.get();
			List<String> imageLinksToRemove = getImageLinksToRemove(profile);
			profileRepo.delete(profile);
			
			executeIfTransactionSuccess( () -> {
				LOGGER.info("Profile with id:{} and uid:'{}' has been removed", profileId, profile.getUid());
				searchIndexingService.removeProfileIndex(profile);
				removeProfileImages(imageLinksToRemove);
				evilcProfileCache(profile.getUid());
			} );
		}
	}
	
	private List<String> getImageLinksToRemove(Profile profile) {
		List<String> imageLinksToRemove = new ArrayList<>();
		imageLinksToRemove.add(profile.getLargePhoto());
		imageLinksToRemove.add(profile.getSmallPhoto());
		if (profile.getCertificates() != null) {
			for (Certificate certificate : profile.getCertificates()) {
				imageLinksToRemove.add(certificate.getLargeUrl());
				imageLinksToRemove.add(certificate.getSmallUrl());
			}
		}
		return imageLinksToRemove;
	}

}
