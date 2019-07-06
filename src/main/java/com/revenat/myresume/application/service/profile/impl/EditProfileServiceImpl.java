package com.revenat.myresume.application.service.profile.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.google.common.base.Objects;
import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.application.exception.DTOValidationException;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.service.profile.EditProfileService;
import com.revenat.myresume.application.transformer.Transformer;
import com.revenat.myresume.application.util.DataUtil;
import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.domain.annotation.OptionalInfoField;
import com.revenat.myresume.domain.annotation.RequiredInfoField;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Contacts;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Education;
import com.revenat.myresume.domain.entity.PracticalExperience;
import com.revenat.myresume.domain.entity.Hobby;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.ProfileEntity;
import com.revenat.myresume.domain.entity.Skill;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Service
class EditProfileServiceImpl implements EditProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EditProfileServiceImpl.class);
	private static final String[] EMPTY_ARRAY = new String[0];

	private final ProfileRepository profileRepo;
	private final ImageStorageService imageStorageService;
	private final SearchIndexingService searchIndexingService;
	private final Transformer transformer;
	private final DataGenerator dataGenerator;
	private final PasswordEncoder passwordEncoder;
	private final CacheService cacheService;
	private final ProfileEntitiesService profileEntitiesService;

	@Autowired
	public EditProfileServiceImpl(ProfileRepository profileRepo, ImageStorageService imageStorageService,
			SearchIndexingService searchIndexingService, Transformer transformer, DataGenerator dataGenerator,
			PasswordEncoder passwordEncoder, CacheService cacheService, ProfileEntitiesService updateProfileService) {
		this.profileRepo = profileRepo;
		this.imageStorageService = imageStorageService;
		this.searchIndexingService = searchIndexingService;
		this.transformer = transformer;
		this.dataGenerator = dataGenerator;
		this.passwordEncoder = passwordEncoder;
		this.cacheService = cacheService;
		this.profileEntitiesService = updateProfileService;
	}
	
	@Override
	@Transactional
	public long createProfile(ProfileDTO newProfileData) {
		MainInfoDTO mainProfileData = newProfileData.getMainInfo();
		
		Profile profile = new Profile();
		profile.setUid(dataGenerator.generateUid(
				newProfileData.getFirstName(),
				newProfileData.getLastName(),
				uid -> profileRepo.countByUid(uid) == 0));
		profile.setFirstName(DataUtil.capitalizeName(newProfileData.getFirstName()));
		profile.setLastName(DataUtil.capitalizeName(newProfileData.getLastName()));
		profile.setPassword(passwordEncoder.encode(newProfileData.getPassword()));
		
		boolean isProfilePhotosUploaded = mainProfileData.getLargePhoto() != null && mainProfileData.getSmallPhoto() != null;
		setProfileMainInfo(profile, mainProfileData, isProfilePhotosUploaded);
		if (isProfilePhotosUploaded) {
			deleteUploadedPhotosIfTransactionFailed(mainProfileData.getLargePhoto(), mainProfileData.getSmallPhoto());
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
	
	/**
	 * Synchronized in case several users try to register profiles with equal email/phone simultaneously
	 * @param profile profile to save
	 * @return saved profile with id
	 */
	private synchronized Profile validateAndSave(Profile profile) {
		validateEmailAndPhoneUniqueness(profile);
		return profileRepo.saveAndFlush(profile);
	}
	
	@Override
	@Transactional
	public void removeProfile(long profileId) {
		Optional<Profile> optional = profileRepo.findOneById(profileId);
		if (optional.isPresent()) {
			Profile profile = optional.get();
			List<String> imageLinksToRemove = getImageLinksToRemove(profile);
			profileRepo.delete(profile);
			
			removeProfileIndexIfTransactionSuccess(profile);
			removeProfileImagesIfTransactionSuccess(imageLinksToRemove);
			evilcProfileCacheIfTransactionSuccess(profile.getUid());
		}
	}

	@Override
	public MainInfoDTO getMainInfoFor(long profileId) {
		return getDTO(profileId, MainInfoDTO.class);
	}
	
	@Override
	@Transactional
	public void updateMainInfo(long profileId, MainInfoDTO updatedMainInfo) {
		Profile loadedProfile = profileRepo.findOne(profileId);
		List<String> oldProfilePhotos = Collections.emptyList();
		
		boolean isProfilePhotosUpdated = checkIfProfilePhotosUpdated(loadedProfile, updatedMainInfo);
		if (isProfilePhotosUpdated) {
			deleteUploadedPhotosIfTransactionFailed(updatedMainInfo.getLargePhoto(), updatedMainInfo.getSmallPhoto());
			oldProfilePhotos = Arrays.asList(loadedProfile.getLargePhoto(), loadedProfile.getSmallPhoto());
		}
		int updatedFieldsCount = setProfileMainInfo(loadedProfile, updatedMainInfo, isProfilePhotosUpdated);
		
		boolean shouldProfileBeUpdated = isProfilePhotosUpdated || updatedFieldsCount > 0;
		
		if (shouldProfileBeUpdated) {
			updateProfileData(loadedProfile, oldProfilePhotos);
		}
	}

	private void updateProfileData(Profile loadedProfile, List<String> oldProfilePhotos) {
		boolean isProfileCompleted = isProfileCompleted(loadedProfile);
		loadedProfile.setCompleted(isProfileCompleted);
		
		validateAndSave(loadedProfile);
		
		removeProfileImagesIfTransactionSuccess(oldProfilePhotos);
		evilcProfileCacheIfTransactionSuccess(loadedProfile.getUid());
		if (isProfileCompleted) {
			updateIndexProfileDataIfTransactionSuccess(loadedProfile, RequiredInfoField.class);
		}
	}

	private void updateIndexProfileDataIfTransactionSuccess(final Profile loadedProfile,
			final Class<RequiredInfoField> annotationClass) {
		executeIfTransactionSuccess(() -> searchIndexingService.updateProfileIndex(loadedProfile, annotationClass));
	}

	private boolean checkIfProfilePhotosUpdated(Profile loadedProfile, MainInfoDTO updatedMainInfo) {
		return !Objects.equal(loadedProfile.getLargePhoto(), updatedMainInfo.getLargePhoto());
	}

	@Override
	public String getInfoFor(long profileId) {
		Profile profile = profileRepo.findOne(profileId);
		return profile.getInfo();
	}
	
	@Override
	@Transactional
	public void updateInfoFor(long profileId, String info) {
		Profile profile = profileRepo.findOne(profileId);
		if (Objects.equal(info, profile.getInfo())) {
			LOGGER.debug("Info for profile with id: {} - nothing to update", profileId);
		} else {
			profile.setInfo(info);
			Profile updatedProfile = profileRepo.save(profile);
			profileRepo.flush();

			executeIfTransactionSuccess(
					() -> {
						LOGGER.info("Info for profile with id: {} has been updated", profileId);
						searchIndexingService.updateProfileIndex(updatedProfile, OptionalInfoField.class);
					});
		}
	}
	
	@Override
	public ContactsDTO getContactsFor(long profileId) {
		return getDTO(profileId, ContactsDTO.class);
	}
	
	@Override
	@Transactional
	public void updateContacts(long profileId, ContactsDTO updatedContacts) {
		Profile profile = profileRepo.findOne(profileId);
		ContactsDTO profileContacts = getDTO(profileId, ContactsDTO.class);
		if (profileContacts.equals(updatedContacts)) {
			LOGGER.debug("Contacts for profile with id: {} - nothing to update", profileId);
		} else {
			profile.setContacts(transformer.transform(updatedContacts, Contacts.class));
			profileRepo.save(profile);
			evilcProfileCacheIfTransactionSuccess(profile.getUid());
		}
	}
	
	@Override
	public List<PracticalExperienceDTO> getExperienceFor(long profileId) {
		return getDTOList(profileId, PracticalExperienceDTO.class, PracticalExperience.class);
	}
	
	@Override
	@Transactional
	public void updateExperience(long profileId, List<PracticalExperienceDTO> updatedExperience) {
		updateProfile(profileId, updatedExperience, PracticalExperienceDTO.class, PracticalExperience.class);
	}
	
	@Override
	public List<CertificateDTO> getCertificatesFor(long profileId) {
		return getDTOList(profileId, CertificateDTO.class, Certificate.class);
	}
	
	@Override
	@Transactional
	public void updateCertificates(long profileId, List<CertificateDTO> updatedCertificates, SuccessCallback successCallback) {
		List<String> certificateImagesToRemove = findCertificateImagesToRemove(profileId, updatedCertificates);
		updateProfile(profileId, updatedCertificates, CertificateDTO.class, Certificate.class);
		
		removeProfileImagesIfTransactionSuccess(certificateImagesToRemove);
		if (successCallback != null) {
			executeIfTransactionSuccess(successCallback::onSuccess);
		}
	}
	
	private List<String> findCertificateImagesToRemove(long profileId, List<CertificateDTO> updatedCertificates) {
		List<String> loadedCertificateImages = getCertificateImagesUrls(profileId);
		// Removing actual certificates, only old non-actual are left
		for (CertificateDTO c : updatedCertificates) {
			loadedCertificateImages.remove(c.getLargeUrl());
			loadedCertificateImages.remove(c.getSmallUrl());
		}
		return loadedCertificateImages;
	}
	
	private List<String> getCertificateImagesUrls(long profileId) {
		List<CertificateDTO> loadedCertificates = getCertificatesFor(profileId);
		List<String> result = new ArrayList<>(loadedCertificates.size() * 2);
		for (CertificateDTO c : loadedCertificates) {
			result.add(c.getLargeUrl());
			result.add(c.getSmallUrl());
		}
		return result;
	}

	@Override
	public List<CourseDTO> getCoursesFor(long profileId) {
		return getDTOList(profileId, CourseDTO.class, Course.class);
	}
	
	@Override
	@Transactional
	public void updateCourses(long profileId, List<CourseDTO> updatedCourses) {
		updateProfile(profileId, updatedCourses, CourseDTO.class, Course.class);
	}
	
	@Override
	public List<EducationDTO> getEducationFor(long profileId) {
		return getDTOList(profileId, EducationDTO.class, Education.class);
	}
	
	@Override
	@Transactional
	public void updateEducation(long profileId, List<EducationDTO> updatedEducation) {
		updateProfile(profileId, updatedEducation, EducationDTO.class, Education.class);
	}
	
	@Override
	public List<LanguageDTO> getLanguagesFor(long profileId) {
		return getDTOList(profileId, LanguageDTO.class, Language.class);
	}
	
	@Override
	@Transactional
	public void updateLanguages(long profileId, List<LanguageDTO> updatedLanguages) {
		updateProfile(profileId, updatedLanguages, LanguageDTO.class, Language.class);
	}
	
	@Override
	public List<HobbyDTO> getHobbiesFor(long profileId) {
		return getDTOList(profileId, HobbyDTO.class, Hobby.class);
	}
	
	@Override
	@Transactional
	public void updateHobbies(long profileId, List<HobbyDTO> updatedHobbies) {
		updateProfile(profileId, updatedHobbies, HobbyDTO.class, Hobby.class);
	}

	@Override
	public List<SkillDTO> getSkillsFor(long profileId) {
		return getDTOList(profileId, SkillDTO.class, Skill.class);
	}
	
	@Override
	@Transactional
	public void updateSkills(long profileId, List<SkillDTO> updatedSkills) {
		updateProfile(profileId, updatedSkills, SkillDTO.class, Skill.class);
	}
	
	private <T> T getDTO(long profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transform(profile, dtoClass);
	}
	
	private <E extends ProfileEntity, T> List<T> getDTOList(long profileId, Class<T> dtoClass, Class<E> entityClass) {
		List<E> profileEntities = profileEntitiesService.findByProfileOrderByIdAsc(profileId, entityClass);
		return transformer.transformToList(profileEntities, entityClass, dtoClass);
	}
	
//	private <T,E> void updateProfile(long profileId, List<T> updatedDTOList, Class<T> dtoClass, Class<E> entityClass,
//			ProfileUpdate<List<E>> profileUpdate) {
//		CommonUtils.removeEmptyElements(updatedDTOList);
//		Profile profile = profileRepo.findOne(profileId);
//		List<T> profileDTOList = transformer.transformToList(profile, dtoClass);
//		if (CollectionUtils.isEqualCollection(updatedDTOList, profileDTOList)) {
//			LOGGER.debug("{} for profile with id:{} - nothing to update", entityClass.getSimpleName(), profileId);
//		} else {
//			List<E> updatedEntityList = transformer.transformToList(updatedDTOList, dtoClass, entityClass);
//			profileUpdate.update(profile, updatedEntityList);
//			profileRepo.save(profile);
//		}
//	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T,E extends ProfileEntity> void updateProfile(long profileId, List<T> updatedDTOList, Class<T> dtoClass,
			Class<E> entityClass) {
		String collectionName = pluralize(entityClass);
		List<E> profileData = profileEntitiesService.findByProfileOrderByIdAsc(profileId, entityClass);
		Profile profile = profileRepo.findOne(profileId);
		CommonUtils.removeEmptyElements(updatedDTOList);
		List<T> profileDataAsDTO = transformer.transformToList(profileData, entityClass, dtoClass);
		
		if (isComparable(dtoClass)) {
			Collections.sort((List<? extends Comparable>) updatedDTOList);
		}
		
		if (CollectionUtils.isEqualCollection(updatedDTOList, profileDataAsDTO)) {
			LOGGER.debug("{} for profile with id:{} - nothing to update", collectionName, profileId);
		} else {
			List<E> updatedEntityList = transformer.transformToList(updatedDTOList, dtoClass, entityClass);
			profileEntitiesService.updateProfile(profile, updatedEntityList, entityClass);
			evilcProfileCacheIfTransactionSuccess(profile.getUid());
			updateProfileEntitiesIndexIfTransactionSuccess(profile.getId(), updatedEntityList, entityClass);
		}
	}
	
	private <E extends ProfileEntity> void updateProfileEntitiesIndexIfTransactionSuccess(
			final Long profileId, final List<E> updatedEntityList, final Class<E> entityClass) {
		executeIfTransactionSuccess(
				() -> searchIndexingService.updateIndexProfileEntities(profileId, updatedEntityList, entityClass));
		
	}

	private void evilcProfileCacheIfTransactionSuccess(final String profileUid) {
		executeIfTransactionSuccess(() -> cacheService.deleteProfileByUid(profileUid));
	}

	private boolean isComparable(Class<?> dtoClass) {
		return Comparable.class.isAssignableFrom(dtoClass);
	}

	private <E extends ProfileEntity> String pluralize(Class<E> entityClass) {
		String name = entityClass.getSimpleName().toLowerCase();
		if (name.equals("practicalexperience")) {
			return "experience";
		} else if (name.endsWith("y")) {
			name = name.substring(0, name.length()-1)+"ie";
		}
		return name;
	}

	private void removeProfileImagesIfTransactionSuccess(final List<String> imageLinksToRemove) {
		executeIfTransactionSuccess(() -> imageStorageService.remove(imageLinksToRemove.toArray(EMPTY_ARRAY)));
		
	}

	private void removeProfileIndexIfTransactionSuccess(final Profile profile) {
		executeIfTransactionSuccess(() -> searchIndexingService.removeIndexProfile(profile));
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

	private void executeIfTransactionSuccess(Runnable action) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				action.run();
			}
		});
	}
	
	private void deleteUploadedPhotosIfTransactionFailed(String ...photos) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCompletion(int status) {
				if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
					imageStorageService.remove(photos);
				}
			}
		});
	}

	private int setProfileMainInfo(Profile profile, MainInfoDTO mainInfo, boolean isProfilePhotosUpdated) {
		if (isProfilePhotosUpdated) {
			profile.setLargePhoto(mainInfo.getLargePhoto());
			profile.setSmallPhoto(mainInfo.getSmallPhoto());
		}
		return ReflectionUtil.copyFields(mainInfo, profile, RequiredInfoField.class);
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

	private boolean isProfileCompleted(Profile profile) {
		boolean hasPhoto = CommonUtils.isNotBlank(profile.getLargePhoto()) && CommonUtils.isNotBlank(profile.getSmallPhoto());
		boolean hasAddress = CommonUtils.isNotBlank(profile.getCountry()) && CommonUtils.isNotBlank(profile.getCity());
		boolean hasBirthday = profile.getBirthDay() != null;
		boolean hasPhoneAndEmail = CommonUtils.isNotBlank(profile.getPhone()) && CommonUtils.isNotBlank(profile.getEmail());
		boolean hasInfo = CommonUtils.isNotBlank(profile.getObjective()) && CommonUtils.isNotBlank(profile.getSummary());
		return hasPhoto && hasAddress && hasBirthday && hasPhoneAndEmail && hasInfo;
	}
	
//	@FunctionalInterface
//	private interface ProfileUpdate<V> {
//		void update(Profile p, V value);
//	}
}
