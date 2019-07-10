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
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Objects;
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
import com.revenat.myresume.domain.annotation.OptionalInfoField;
import com.revenat.myresume.domain.annotation.RequiredInfoField;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Contacts;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Education;
import com.revenat.myresume.domain.entity.Hobby;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.PracticalExperience;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.ProfileEntity;
import com.revenat.myresume.domain.entity.Skill;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Service
class EditProfileServiceImpl extends AbstractModifyProfileService implements EditProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EditProfileServiceImpl.class);

	private final Transformer transformer;
	private final ProfileEntitiesService profileEntitiesService;

	@Autowired
	public EditProfileServiceImpl(ProfileRepository profileRepo, ImageStorageService imageStorageService,
			SearchIndexingService searchIndexingService, Transformer transformer,
			CacheService cacheService, ProfileEntitiesService updateProfileService) {
		super(profileRepo, imageStorageService, searchIndexingService, cacheService);
		this.transformer = transformer;
		this.profileEntitiesService = updateProfileService;
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
						updateProfileIndexData(updatedProfile, OptionalInfoField.class);
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
			executeIfTransactionSuccess(() -> evilcProfileCache(profile.getUid()));
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
		
		executeIfTransactionSuccess(
				() -> {
					removeProfileImages(certificateImagesToRemove);
					if (successCallback != null) {
						successCallback.onSuccess();
					}
				});
	}
	
	private List<String> findCertificateImagesToRemove(long profileId, List<CertificateDTO> updatedCertificates) {
		List<String> loadedCertificateImages = getCertificateImagesUrls(profileId);
		// Removing actual certificates, only old and non-actual are left
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
			Collections.sort((List<? extends Comparable>) profileDataAsDTO);
		}
		
		if (CommonUtils.isEqualList(updatedDTOList, profileDataAsDTO)) {
			LOGGER.debug("{} for profile with id:{} - nothing to update", collectionName, profileId);
		} else {
			List<E> updatedEntityList = transformer.transformToList(updatedDTOList, dtoClass, entityClass);
			profileEntitiesService.updateProfile(profile, updatedEntityList, entityClass);
			
			executeIfTransactionSuccess(
					() -> {
						evilcProfileCache(profile.getUid());
						updateProfileEntitiesIndex(profile.getId(), updatedEntityList, entityClass);
					});
		}
	}

	private <E extends ProfileEntity> void updateProfileEntitiesIndex(
			final Long profileId, final List<E> updatedEntityList, final Class<E> entityClass) {
		searchIndexingService.updateProfileEntitiesIndex(profileId, updatedEntityList, entityClass);
		
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
		return name + "s";
	}

}
