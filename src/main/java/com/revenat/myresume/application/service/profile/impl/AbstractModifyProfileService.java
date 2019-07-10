package com.revenat.myresume.application.service.profile.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.exception.DTOValidationException;
import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.domain.annotation.RequiredInfoField;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.CommonUtils;

abstract class AbstractModifyProfileService {
	private static final String[] EMPTY_ARRAY = new String[0];
	
	protected final ProfileRepository profileRepo;
	protected final ImageStorageService imageStorageService;
	protected final SearchIndexingService searchIndexingService;
	protected final CacheService cacheService;
	
	public AbstractModifyProfileService(ProfileRepository profileRepo, ImageStorageService imageStorageService,
			SearchIndexingService searchIndexingService, CacheService cacheService) {
		this.profileRepo = profileRepo;
		this.imageStorageService = imageStorageService;
		this.searchIndexingService = searchIndexingService;
		this.cacheService = cacheService;
	}

	protected int setProfileMainInfo(Profile profile, MainInfoDTO mainInfo, boolean isProfilePhotosUpdated) {
		if (isProfilePhotosUpdated) {
			profile.setLargePhoto(mainInfo.getLargePhoto());
			profile.setSmallPhoto(mainInfo.getSmallPhoto());
		}
		return ReflectionUtil.copyFields(mainInfo, profile, RequiredInfoField.class);
	}
	
	protected void removeProfileImages(final List<String> imageLinksToRemove) {
		imageStorageService.remove(imageLinksToRemove.toArray(EMPTY_ARRAY));
	}
	
	protected void evilcProfileCache(final String profileUid) {
		cacheService.deleteProfileByUid(profileUid);
	}
	
	/**
	 * Synchronized in case several users try to register/update profiles with equal email/phone simultaneously
	 * @param profile profile to save
	 * @return saved profile with id
	 */
	protected synchronized Profile validateAndSave(Profile profile) {
		validateEmailAndPhoneUniqueness(profile);
		return profileRepo.saveAndFlush(profile);
	}
	
	protected static boolean isProfileCompleted(Profile profile) {
		boolean hasPhoto = CommonUtils.isNotBlank(profile.getLargePhoto()) && CommonUtils.isNotBlank(profile.getSmallPhoto());
		boolean hasAddress = CommonUtils.isNotBlank(profile.getCountry()) && CommonUtils.isNotBlank(profile.getCity());
		boolean hasBirthday = profile.getBirthDay() != null;
		boolean hasPhoneAndEmail = CommonUtils.isNotBlank(profile.getPhone()) && CommonUtils.isNotBlank(profile.getEmail());
		boolean hasInfo = CommonUtils.isNotBlank(profile.getObjective()) && CommonUtils.isNotBlank(profile.getSummary());
		return hasPhoto && hasAddress && hasBirthday && hasPhoneAndEmail && hasInfo;
	}

	protected static void executeIfTransactionSuccess(Runnable action) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				action.run();
			}
		});
	}
	
	protected static void executeIfTransactionFailed(Runnable action) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCompletion(int status) {
				if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
					action.run();
				}
			}
		});
	}
	
	private void validateEmailAndPhoneUniqueness(Profile profile) {
		try {
			validateEmailUniqueness(profile);
			validatePhoneUniqueness(profile);
		} catch (DTOValidationException e) {
			removeUploadedPhotos(profile);
			throw e;
		}
	}

	private void removeUploadedPhotos(Profile profile) {
		imageStorageService.remove(profile.getLargePhoto(), profile.getSmallPhoto());
	}

	private void validatePhoneUniqueness(Profile profile) {
		Optional<Profile> byPhone = profileRepo.findByPhone(profile.getPhone());
		if (byPhone.isPresent() && !byPhone.get().equals(profile)) {
			throw new DTOValidationException("phone", profile.getPhone());
		}
	}

	private void validateEmailUniqueness(Profile profile) {
		Optional<Profile> byEmail = profileRepo.findByEmail(profile.getEmail());
		if (byEmail.isPresent() && !byEmail.get().equals(profile)) {
			throw new DTOValidationException("email", profile.getEmail());
		}
	}
}
