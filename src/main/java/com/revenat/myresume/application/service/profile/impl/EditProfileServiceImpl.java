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
import com.revenat.myresume.application.config.transaction.EnableTransactionSynchronization;
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
import com.revenat.myresume.domain.annotation.OptionalInfo;
import com.revenat.myresume.domain.annotation.RequiredInfo;
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
	@EnableTransactionSynchronization
	public void updateMainInfo(String profileId, MainInfoDTO updateData) {
		Profile profileToUpdate = profileRepo.findOne(profileId);
		List<String> photoLinksToRemove = findPhotoLinksToRemove(profileToUpdate, updateData);
		
		boolean isProfileDataUpdated = updateProfileMainInfo(profileToUpdate, updateData);
		
		if (isProfileDataUpdated) {
			persistUpdatedProfile(profileToUpdate, photoLinksToRemove);
		}
	}

	@Override
	public String getInfoFor(String profileId) {
		Profile profile = profileRepo.findOne(profileId);
		return profile.getInfo();
	}
	
	@Override
	@EnableTransactionSynchronization
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
						updateProfileIndexData(updatedProfile, OptionalInfo.class);
					});
		}
	}
	
	@Override
	public ContactsDTO getContactsFor(String profileId) {
		return getDTO(profileId, ContactsDTO.class);
	}
	
	@Override
	@EnableTransactionSynchronization
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
	@EnableTransactionSynchronization
	public void updateExperience(String profileId, List<PracticalExperienceDTO> updatedExperience) {
		updateProfile(profileId, updatedExperience, PracticalExperienceDTO.class, PracticalExperience.class);
	}
	
	@Override
	public List<CertificateDTO> getCertificatesFor(String profileId) {
		return getDTOList(profileId, CertificateDTO.class);
	}
	
	@Override
	@EnableTransactionSynchronization
	public void updateCertificates(String profileId, List<CertificateDTO> updatedCertificates, SuccessCallback successCallback) {
		List<String> certificateLinksToRemove = findCertificateLinksToRemove(profileId, updatedCertificates);
		updateProfile(profileId, updatedCertificates, CertificateDTO.class, Certificate.class);
		
		executeIfTransactionSuccess(
				() -> {
					removeImages(certificateLinksToRemove);
					if (successCallback != null) {
						successCallback.onSuccess();
					}
				});
	}

	@Override
	public List<CourseDTO> getCoursesFor(String profileId) {
		return getDTOList(profileId, CourseDTO.class);
	}
	
	@Override
	@EnableTransactionSynchronization
	public void updateCourses(String profileId, List<CourseDTO> updatedCourses) {
		updateProfile(profileId, updatedCourses, CourseDTO.class, Course.class);
	}
	
	@Override
	public List<EducationDTO> getEducationFor(String profileId) {
		return getDTOList(profileId, EducationDTO.class);
	}
	
	@Override
	@EnableTransactionSynchronization
	public void updateEducation(String profileId, List<EducationDTO> updatedEducation) {
		updateProfile(profileId, updatedEducation, EducationDTO.class, Education.class);
	}
	
	@Override
	public List<LanguageDTO> getLanguagesFor(String profileId) {
		return getDTOList(profileId, LanguageDTO.class);
	}
	
	@Override
	@EnableTransactionSynchronization
	public void updateLanguages(String profileId, List<LanguageDTO> updatedLanguages) {
		updateProfile(profileId, updatedLanguages, LanguageDTO.class, Language.class);
	}
	
	@Override
	public List<HobbyDTO> getHobbiesFor(String profileId) {
		return getDTOList(profileId, HobbyDTO.class);
	}
	
	@Override
	@EnableTransactionSynchronization
	public void updateHobbies(String profileId, List<HobbyDTO> updatedHobbies) {
		updateProfile(profileId, updatedHobbies, HobbyDTO.class, Hobby.class);
	}

	@Override
	public List<SkillDTO> getSkillsFor(String profileId) {
		return getDTOList(profileId, SkillDTO.class);
	}
	
	@Override
	@EnableTransactionSynchronization
	public void updateSkills(String profileId, List<SkillDTO> updatedSkills) {
		updateProfile(profileId, updatedSkills, SkillDTO.class, Skill.class);
	}
	
	private List<String> findPhotoLinksToRemove(Profile profileToUpdate, MainInfoDTO updatedData) {
		List<String> photoLinksToRemove = Collections.emptyList();

		boolean isProfilePhotosUpdated = checkIfProfilePhotosUpdated(profileToUpdate, updatedData);
		if (isProfilePhotosUpdated) {
			List<String> photoLinksToUpdate = Arrays.asList(updatedData.getLargePhoto(), updatedData.getSmallPhoto());
			photoLinksToRemove = Arrays.asList(profileToUpdate.getLargePhoto(), profileToUpdate.getSmallPhoto());
			executeIfTransactionFailed(() -> removeImages(photoLinksToUpdate));
		}
		return photoLinksToRemove;
	}

	private void persistUpdatedProfile(Profile loadedProfile, List<String> photoLinksToRemove) {
		boolean isProfileCompleted = isProfileCompleted(loadedProfile);
		loadedProfile.setCompleted(isProfileCompleted);
		
		validateAndSave(loadedProfile);
		
		executeIfTransactionSuccess(
				() -> {
					removeImages(photoLinksToRemove);
					evilcProfileCache(loadedProfile.getUid());
					if (isProfileCompleted) {
						updateProfileIndexData(loadedProfile, RequiredInfo.class);
					}
				});
	}
	
	private List<String> findCertificateLinksToRemove(String profileId, List<CertificateDTO> updatedCertificates) {
		List<String> profileCertificateLinks = getProfileCertificateLinks(profileId);
		// Removing actual certificates, only old and non-actual are left
		for (CertificateDTO c : updatedCertificates) {
			profileCertificateLinks.remove(c.getLargeUrl());
			profileCertificateLinks.remove(c.getSmallUrl());
		}
		return profileCertificateLinks;
	}
	
	private List<String> getProfileCertificateLinks(String profileId) {
		List<CertificateDTO> loadedCertificates = getCertificatesFor(profileId);
		List<String> result = new ArrayList<>(loadedCertificates.size() * 2);
		for (CertificateDTO c : loadedCertificates) {
			result.add(c.getLargeUrl());
			result.add(c.getSmallUrl());
		}
		return result;
	}
	
	private void updateProfileIndexData(final Profile loadedProfile,
			final Class<? extends Annotation> annotationClass) {
		searchIndexingService.updateProfileIndex(loadedProfile, annotationClass);
	}
	
	private <T> T getDTO(String profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transform(profile, dtoClass);
	}
	
	private <T> List<T> getDTOList(String profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transformToList(profile, dtoClass);
	}
	
	private <T,E extends ProfileDocument> void updateProfile(String profileId, List<T> updatedDTOData, Class<T> dtoClass,
			Class<E> entityClass) {
		CommonUtils.removeEmptyElements(updatedDTOData);
		
		Profile profileToUpdate = profileRepo.findOne(profileId);
		
		boolean isUpdateRequired = checkIfUpdateRequired(updatedDTOData, dtoClass, entityClass, profileToUpdate);
		
		if (isUpdateRequired) {
			performProfileUpdate(updatedDTOData, dtoClass, entityClass, profileToUpdate);
		} else {
			String propertyName = pluralize(entityClass);
			LOGGER.debug("{} for profile with id:{} - nothing to update", propertyName, profileId);
		}
	}

	private <E extends ProfileDocument, T> void performProfileUpdate(List<T> updatedDTOData, Class<T> dtoClass,
			Class<E> entityClass, Profile profileToUpdate) {
		String propertyName = pluralize(entityClass);
		List<E> updatedData = transformer.transformToList(updatedDTOData, dtoClass, entityClass);
		
		updateProfileData(profileToUpdate, updatedData, propertyName);
		
		executeIfTransactionSuccess(
				() -> {
					evilcProfileCache(profileToUpdate.getUid());
					updateProfileDataIndex(profileToUpdate.getId(), updatedData, entityClass);
				});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T, E extends ProfileDocument> boolean checkIfUpdateRequired(List<T> updatedDTOData, Class<T> dtoClass,
			Class<E> entityClass, Profile profileToUpdate) {
		String propertyName = pluralize(entityClass);
		List<T> profileDTOData = transformer.transformToList(getProfileData(profileToUpdate, propertyName),
				entityClass, dtoClass);
		
		if (isComparable(dtoClass)) {
			Collections.sort((List<? extends Comparable>) updatedDTOData);
			Collections.sort((List<? extends Comparable>) profileDTOData);
		}
		
		return !CommonUtils.isEqualList(updatedDTOData, profileDTOData);
	}
	
	@SuppressWarnings("unchecked")
	private <E extends ProfileDocument> List<E> getProfileData(Profile profile, String collectionName) {
		List<E> profileData = (List<E>) ReflectionUtil.readProperty(profile, collectionName);
		if (profileData == null) {
			profileData = Collections.emptyList();
		}
		return profileData;
	}
	
	private <E extends ProfileDocument> void updateProfileData(Profile profileToUpdate, List<E> updatedData, String propertyName) {
		ReflectionUtil.writeProperty(profileToUpdate, propertyName, updatedData);
		profileRepo.save(profileToUpdate);
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
