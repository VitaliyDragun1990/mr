package com.revenat.myresume.application.service.profile.impl;

import java.util.List;
import java.util.Optional;

import com.google.common.base.Objects;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.exception.DTOValidationException;
import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.domain.annotation.RequiredInfo;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.CommonUtils;
import com.revenat.myresume.infrastructure.util.TransactionUtils;

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

	/**
	 * Updates profile main info with specified {@code mainInfo} data.
	 * 
	 * @param profile                profile to update
	 * @param mainInfo               updated data
	 * @param isProfilePhotosUpdated {@code true} if profile photos should be
	 *                               updated, {@code false} otherwise
	 * @return {@code true} if update took place, {@code false} if profile data is
	 *         up to date and no update has happened
	 */
	protected boolean updateProfileMainInfo(Profile profile, MainInfoDTO mainInfo) {
		boolean isProfilePhotosUpdated = checkIfProfilePhotosUpdated(profile, mainInfo);
		if (isProfilePhotosUpdated) {
			profile.setLargePhoto(mainInfo.getLargePhoto());
			profile.setSmallPhoto(mainInfo.getSmallPhoto());
		}
		return ReflectionUtil.copyFields(mainInfo, profile, RequiredInfo.class) > 0 || isProfilePhotosUpdated;
	}
	
	protected boolean checkIfProfilePhotosUpdated(Profile loadedProfile, MainInfoDTO updatedMainInfo) {
		return !Objects.equal(loadedProfile.getLargePhoto(), updatedMainInfo.getLargePhoto())
				&& !Objects.equal(loadedProfile.getSmallPhoto(), updatedMainInfo.getSmallPhoto());
	}

	protected void removeImages(final List<String> imageLinksToRemove) {
		imageStorageService.remove(imageLinksToRemove.toArray(EMPTY_ARRAY));
	}

	protected void evilcProfileCache(final String profileUid) {
		cacheService.deleteProfileByUid(profileUid);
	}

	/**
	 * Synchronized in case several users try to register/update profiles with equal
	 * email/phone simultaneously
	 * 
	 * @param profile profile to save
	 * @return saved profile with id
	 */
	protected synchronized Profile validateAndSave(Profile profile) {
		validateEmailAndPhoneUniqueness(profile);
		return profileRepo.save(profile);
	}

	protected static boolean isProfileCompleted(Profile profile) {
		boolean hasPhoto = CommonUtils.isNotBlank(profile.getLargePhoto())
				&& CommonUtils.isNotBlank(profile.getSmallPhoto());
		boolean hasAddress = CommonUtils.isNotBlank(profile.getCountry()) && CommonUtils.isNotBlank(profile.getCity());
		boolean hasBirthday = profile.getBirthDay() != null;
		boolean hasPhoneAndEmail = CommonUtils.isNotBlank(profile.getPhone())
				&& CommonUtils.isNotBlank(profile.getEmail());
		boolean hasInfo = CommonUtils.isNotBlank(profile.getObjective())
				&& CommonUtils.isNotBlank(profile.getSummary());
		return hasPhoto && hasAddress && hasBirthday && hasPhoneAndEmail && hasInfo;
	}

	protected static void executeIfTransactionSuccess(Runnable action) {
		TransactionUtils.executeIfTransactionSucceeded(action);
	}

	protected static void executeIfTransactionFailed(Runnable action) {
		TransactionUtils.executeIfTransactionFailed(action);
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
