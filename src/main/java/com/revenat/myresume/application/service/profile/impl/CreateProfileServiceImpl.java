package com.revenat.myresume.application.service.profile.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.config.transaction.EnableTransactionSynchronization;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.service.profile.CreateProfileService;
import com.revenat.myresume.application.util.DataUtil;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.Checks;

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
	@EnableTransactionSynchronization
	public String createProfile(ProfileDTO newProfileData) {
		Checks.checkParam(newProfileData != null, "newProfileData to create new profile from can not be null");
		
		MainInfoDTO mainProfileData = newProfileData.getMainInfo();

		Profile profile = new Profile();
		profile.setUid(dataGenerator.generateUid(newProfileData.getFirstName(), newProfileData.getLastName(),
				uid -> profileRepo.countByUid(uid) == 0));
		profile.setFirstName(DataUtil.capitalizeName(newProfileData.getFirstName()));
		profile.setLastName(DataUtil.capitalizeName(newProfileData.getLastName()));
		profile.setPassword(passwordEncoder.encode(newProfileData.getPassword()));

		updateProfileMainInfo(profile, mainProfileData);
		
		boolean isProfilePhotosUploaded = checkIfPhotosUploaded(mainProfileData);
		if (isProfilePhotosUploaded) {
			executeIfTransactionFailed(() -> removeImages(getUploadedPhotoLinks(mainProfileData)));
		}

		return saveNewProfileData(profile);
	}

	private List<String> getUploadedPhotoLinks(MainInfoDTO mainProfileData) {
		return Arrays.asList(mainProfileData.getLargePhoto(), mainProfileData.getSmallPhoto());
	}

	private boolean checkIfPhotosUploaded(MainInfoDTO mainProfileData) {
		return mainProfileData.getLargePhoto() != null && mainProfileData.getSmallPhoto() != null;
	}

	private String saveNewProfileData(Profile profile) {
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
