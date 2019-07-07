package com.revenat.myresume.application.service.profile.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.service.profile.CreateProfileService;
import com.revenat.myresume.application.util.DataUtil;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;

@Service
class CreateProfileServiceImpl extends AbstractModifyProfileService implements CreateProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateProfileServiceImpl.class);

	private final DataGenerator dataGenerator;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public CreateProfileServiceImpl(ProfileRepository profileRepo, ImageStorageService imageStorageService,
			SearchIndexingService searchIndexingService, CacheService cacheService, DataGenerator dataGenerator,
			PasswordEncoder passwordEncoder) {
		super(profileRepo, imageStorageService, searchIndexingService, cacheService);
		this.dataGenerator = dataGenerator;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public long createProfile(ProfileDTO newProfileData) {
		MainInfoDTO mainProfileData = newProfileData.getMainInfo();

		Profile profile = new Profile();
		profile.setUid(dataGenerator.generateUid(newProfileData.getFirstName(), newProfileData.getLastName(),
				uid -> profileRepo.countByUid(uid) == 0));
		profile.setFirstName(DataUtil.capitalizeName(newProfileData.getFirstName()));
		profile.setLastName(DataUtil.capitalizeName(newProfileData.getLastName()));
		profile.setPassword(passwordEncoder.encode(newProfileData.getPassword()));

		boolean isProfilePhotosUploaded = mainProfileData.getLargePhoto() != null
				&& mainProfileData.getSmallPhoto() != null;
		setProfileMainInfo(profile, mainProfileData, isProfilePhotosUploaded);
		if (isProfilePhotosUploaded) {
			executeIfTransactionFailed(() -> removeProfileImages(
					Arrays.asList(mainProfileData.getLargePhoto(), mainProfileData.getSmallPhoto())));
		}

		return saveNewProfileData(profile);
	}

	private long saveNewProfileData(Profile profile) {
		boolean isCompleted = isProfileCompleted(profile);
		profile.setCompleted(isCompleted);

		Profile savedProfile = validateAndSave(profile);

		executeIfTransactionSuccess(() -> {
			LOGGER.info("New profile created: {}", profile.getUid());
			if (isCompleted) {
				searchIndexingService.createNewProfileIndex(savedProfile);
			}
		});

		return savedProfile.getId();
	}

}
