package com.revenat.myresume.application.service.profile.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.config.transaction.EnableTransactionSynchronization;
import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.service.profile.RemoveProfileService;
import com.revenat.myresume.domain.document.Certificate;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.Checks;

@Service
class RemoveProfileServiceImpl extends AbstractModifyProfileService implements RemoveProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveProfileServiceImpl.class);

	@Autowired
	public RemoveProfileServiceImpl(ProfileRepository profileRepo, ImageStorageService imageStorageService,
			SearchIndexingService searchIndexingService, CacheService cacheService) {
		super(profileRepo, imageStorageService, searchIndexingService, cacheService);
	}

	@Override
	@EnableTransactionSynchronization
	public void removeProfile(String profileId) {
		Checks.checkParam(profileId != null, "profileId to remove profile with can not be null");
		
		Optional<Profile> optional = profileRepo.findOneById(profileId);
		if (optional.isPresent()) {
			Profile profile = optional.get();
			List<String> imageLinksToRemove = getImageLinksToRemove(profile);
			profileRepo.delete(profile);
			
			executeIfTransactionSuccess( () -> {
				LOGGER.info("Profile with id:{} and uid:'{}' has been removed", profileId, profile.getUid());
				searchIndexingService.removeProfileIndex(profile);
				removeImages(imageLinksToRemove);
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
