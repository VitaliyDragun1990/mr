package com.revenat.myresume.application.service.profile.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.revenat.myresume.application.config.transaction.EmulatedTransactional;
import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.service.profile.EditProfileService;
import com.revenat.myresume.application.transformer.Transformer;
import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.domain.annotation.OptionalInfoField;
import com.revenat.myresume.domain.annotation.RequiredInfoField;
import com.revenat.myresume.domain.document.Certificate;
import com.revenat.myresume.domain.document.Contacts;
import com.revenat.myresume.domain.document.Course;
import com.revenat.myresume.domain.document.Education;
import com.revenat.myresume.domain.document.Hobby;
import com.revenat.myresume.domain.document.Language;
import com.revenat.myresume.domain.document.PracticalExperience;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.domain.document.ProfileDocument;
import com.revenat.myresume.domain.document.Skill;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Service
class EditProfileServiceImpl extends AbstractModifyProfileService implements EditProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EditProfileServiceImpl.class);

	private final Transformer transformer;

	@Autowired
	public EditProfileServiceImpl(ProfileRepository profileRepo, ImageStorageService imageStorageService,
			SearchIndexingService searchIndexingService, Transformer transformer,
			CacheService cacheService) {
		super(profileRepo, imageStorageService, searchIndexingService, cacheService);
		this.transformer = transformer;
	}

	@Override
	public MainInfoDTO getMainInfoFor(String profileId) {
		return getDTO(profileId, MainInfoDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateMainInfo(String profileId, MainInfoDTO updatedMainInfo) {
		Profile loadedProfile = profileRepo.findOne(profileId);
		List<String> oldProfilePhotos = Collections.emptyList();
		
		boolean isProfilePhotosUpdated = checkIfProfilePhotosUpdated(loadedProfile, updatedMainInfo);
		if (isProfilePhotosUpdated) {
			oldProfilePhotos = Arrays.asList(loadedProfile.getLargePhoto(), loadedProfile.getSmallPhoto());
			executeIfTransactionFailed(
					() -> removeProfileImages(
							Arrays.asList(updatedMainInfo.getLargePhoto(), updatedMainInfo.getSmallPhoto())
							));
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
		
		executeIfTransactionSuccess(
				() -> {
					removeProfileImages(oldProfilePhotos);
					evilcProfileCache(loadedProfile.getUid());
					if (isProfileCompleted) {
						updateProfileIndexData(loadedProfile, RequiredInfoField.class);
					}
				});
	}
	
	private void updateProfileIndexData(final Profile loadedProfile,
			final Class<? extends Annotation> annotationClass) {
		searchIndexingService.updateProfileIndex(loadedProfile, annotationClass);
	}

	private boolean checkIfProfilePhotosUpdated(Profile loadedProfile, MainInfoDTO updatedMainInfo) {
		return !Objects.equal(loadedProfile.getLargePhoto(), updatedMainInfo.getLargePhoto());
	}

	@Override
	public String getInfoFor(String profileId) {
		Profile profile = profileRepo.findOne(profileId);
		return profile.getInfo();
	}
	
	@Override
	@EmulatedTransactional
	public void updateInfoFor(String profileId, String info) {
		Profile profile = profileRepo.findOne(profileId);
		if (Objects.equal(info, profile.getInfo())) {
			LOGGER.debug("Info for profile with id: {} - nothing to update", profileId);
		} else {
			profile.setInfo(info);
			Profile updatedProfile = profileRepo.save(profile);

			executeIfTransactionSuccess(
					() -> {
						LOGGER.info("Info for profile with id: {} has been updated", profileId);
						updateProfileIndexData(updatedProfile, OptionalInfoField.class);
					});
		}
	}
	
	@Override
	public ContactsDTO getContactsFor(String profileId) {
		return getDTO(profileId, ContactsDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateContacts(String profileId, ContactsDTO updatedContacts) {
		Profile profile = profileRepo.findOne(profileId);
		ContactsDTO profileContacts = getDTO(profileId, ContactsDTO.class);
		if (profileContacts.equals(updatedContacts)) {
			LOGGER.debug("Contacts for profile with id: {} - nothing to update", profileId);
		} else {
			profile.setContacts(transformer.transform(updatedContacts, Contacts.class));
			profileRepo.save(profile);
			executeIfTransactionSuccess(() -> evilcProfileCache(profile.getUid()));
		}
	}
	
	@Override
	public List<PracticalExperienceDTO> getExperienceFor(String profileId) {
		return getDTOList(profileId, PracticalExperienceDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateExperience(String profileId, List<PracticalExperienceDTO> updatedExperience) {
		updateProfile(profileId, updatedExperience, PracticalExperienceDTO.class, PracticalExperience.class);
	}
	
	@Override
	public List<CertificateDTO> getCertificatesFor(String profileId) {
		return getDTOList(profileId, CertificateDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateCertificates(String profileId, List<CertificateDTO> updatedCertificates, SuccessCallback successCallback) {
		List<String> certificateImagesToRemove = findCertificateImagesToRemove(profileId, updatedCertificates);
		updateProfile(profileId, updatedCertificates, CertificateDTO.class, Certificate.class);
		
		executeIfTransactionSuccess(
				() -> {
					removeProfileImages(certificateImagesToRemove);
					if (successCallback != null) {
						successCallback.onSuccess();
					}
				});
	}
	
	private List<String> findCertificateImagesToRemove(String profileId, List<CertificateDTO> updatedCertificates) {
		List<String> loadedCertificateImages = getCertificateImagesUrls(profileId);
		// Removing actual certificates, only old and non-actual are left
		for (CertificateDTO c : updatedCertificates) {
			loadedCertificateImages.remove(c.getLargeUrl());
			loadedCertificateImages.remove(c.getSmallUrl());
		}
		return loadedCertificateImages;
	}
	
	private List<String> getCertificateImagesUrls(String profileId) {
		List<CertificateDTO> loadedCertificates = getCertificatesFor(profileId);
		List<String> result = new ArrayList<>(loadedCertificates.size() * 2);
		for (CertificateDTO c : loadedCertificates) {
			result.add(c.getLargeUrl());
			result.add(c.getSmallUrl());
		}
		return result;
	}

	@Override
	public List<CourseDTO> getCoursesFor(String profileId) {
		return getDTOList(profileId, CourseDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateCourses(String profileId, List<CourseDTO> updatedCourses) {
		updateProfile(profileId, updatedCourses, CourseDTO.class, Course.class);
	}
	
	@Override
	public List<EducationDTO> getEducationFor(String profileId) {
		return getDTOList(profileId, EducationDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateEducation(String profileId, List<EducationDTO> updatedEducation) {
		updateProfile(profileId, updatedEducation, EducationDTO.class, Education.class);
	}
	
	@Override
	public List<LanguageDTO> getLanguagesFor(String profileId) {
		return getDTOList(profileId, LanguageDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateLanguages(String profileId, List<LanguageDTO> updatedLanguages) {
		updateProfile(profileId, updatedLanguages, LanguageDTO.class, Language.class);
	}
	
	@Override
	public List<HobbyDTO> getHobbiesFor(String profileId) {
		return getDTOList(profileId, HobbyDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateHobbies(String profileId, List<HobbyDTO> updatedHobbies) {
		updateProfile(profileId, updatedHobbies, HobbyDTO.class, Hobby.class);
	}

	@Override
	public List<SkillDTO> getSkillsFor(String profileId) {
		return getDTOList(profileId, SkillDTO.class);
	}
	
	@Override
	@EmulatedTransactional
	public void updateSkills(String profileId, List<SkillDTO> updatedSkills) {
		updateProfile(profileId, updatedSkills, SkillDTO.class, Skill.class);
	}
	
	private <T> T getDTO(String profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transform(profile, dtoClass);
	}
	
	private <T> List<T> getDTOList(String profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transformToList(profile, dtoClass);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T,E extends ProfileDocument> void updateProfile(String profileId, List<T> updatedDTOList, Class<T> dtoClass,
			Class<E> entityClass) {
		Profile profile = profileRepo.findOne(profileId);
		String collectionName = pluralize(entityClass);
		List<E> profileData = getProfileData(profile, collectionName);
		
		CommonUtils.removeEmptyElements(updatedDTOList);
		List<T> profileDataAsDTO = transformer.transformToList(profileData, entityClass, dtoClass);
		
		if (isComparable(dtoClass)) {
			Collections.sort((List<? extends Comparable>) updatedDTOList);
			Collections.sort((List<? extends Comparable>) profileDataAsDTO);
		}
		
		if (CommonUtils.isEqualList(updatedDTOList, profileDataAsDTO)) {
			LOGGER.debug("{} for profile with id:{} - nothing to update", collectionName, profileId);
		} else {
			List<E> updatedEntityList = transformer.transformToList(updatedDTOList, dtoClass, entityClass);
			updateProfileData(profile, updatedEntityList, collectionName);
			
			executeIfTransactionSuccess(
					() -> {
						evilcProfileCache(profile.getUid());
						updateProfileDataIndex(profile.getId(), updatedEntityList, entityClass);
					});
		}
	}
	
	@SuppressWarnings("unchecked")
	private <E extends ProfileDocument> List<E> getProfileData(Profile profile, String collectionName) {
		List<E> profileData = (List<E>) ReflectionUtil.readProperty(profile, collectionName);
		if (profileData == null) {
			profileData = Collections.emptyList();
		}
		return profileData;
	}
	
	private <E extends ProfileDocument> void updateProfileData(Profile profile, List<E> updatedData, String collectionName) {
		ReflectionUtil.writeProperty(profile, collectionName, updatedData);
		profileRepo.save(profile);
	}

	private <E extends ProfileDocument> void updateProfileDataIndex(
			final String profileId, final List<E> updatedEntityList, final Class<E> entityClass) {
		searchIndexingService.updateProfileDataIndex(profileId, updatedEntityList, entityClass);
		
	}

	private boolean isComparable(Class<?> dtoClass) {
		return Comparable.class.isAssignableFrom(dtoClass);
	}

	private <E extends ProfileDocument> String pluralize(Class<E> entityClass) {
		String name = entityClass.getSimpleName().toLowerCase();
		if (name.equals("practicalexperience")) {
			return "experience";
		} else if (name.endsWith("y")) {
			name = name.substring(0, name.length()-1)+"ie";
		}
		return name + "s";
	}

}
