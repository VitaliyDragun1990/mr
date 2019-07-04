package com.revenat.myresume.application.service.profile.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.dto.ExperienceDTO;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.application.exception.NotFoundException;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.service.profile.EditProfileService;
import com.revenat.myresume.application.service.profile.SearchProfileService;
import com.revenat.myresume.application.transformer.Transformer;
import com.revenat.myresume.application.util.DataUtil;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Contacts;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Education;
import com.revenat.myresume.domain.entity.Experience;
import com.revenat.myresume.domain.entity.Hobby;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.LanguageLevel;
import com.revenat.myresume.domain.entity.LanguageType;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.Skill;
import com.revenat.myresume.domain.entity.SkillCategory;
import com.revenat.myresume.infrastructure.repository.search.ProfileSearchRepository;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;

@Service
class ProfileService implements SearchProfileService, EditProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class);

	private final ProfileRepository profileRepo;
	private final ProfileSearchRepository searchRepository;
	private final SearchIndexingService searchIndexingService;
	private final Transformer transformer;
	private final DataGenerator uidGenerator;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ProfileService(ProfileRepository profileRepo, ProfileSearchRepository searchRepository,
			SearchIndexingService searchIndexingService, Transformer transformer, DataGenerator uidGenerator,
			PasswordEncoder passwordEncoder) {
		this.profileRepo = profileRepo;
		this.searchRepository = searchRepository;
		this.searchIndexingService = searchIndexingService;
		this.transformer = transformer;
		this.uidGenerator = uidGenerator;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public ProfileDTO getByUid(String uid) {
		Optional<Profile> profile = profileRepo.findOneByUid(uid);
		if (profile.isPresent()) {
			return transformer.transform(profile.get(), ProfileDTO.class);
		} else {
			throw new NotFoundException(Profile.class, "uid", uid);
		}
	}
	
	@Override
	public Optional<ProfileDTO> findByEmail(String email) {
		Optional<Profile> optional = profileRepo.findByEmail(email);
		if (optional.isPresent()) {
			return Optional.of(transformer.transform(optional.get(), ProfileDTO.class));
		}
		return Optional.empty();
	}

	@Override
	public Page<ProfileDTO> findAll(Pageable pageable) {
		Page<Profile> page = profileRepo.findAll(pageable);
		List<Profile> content = page.getContent();
		List<ProfileDTO> dtoList = transformer.transformToList(content, Profile.class, ProfileDTO.class);
		return new PageImpl<>(dtoList, pageable, page.getTotalElements());
	}
	
	@Override
	public Page<ProfileDTO> findBySearchQuery(String query, Pageable pageable) {
		Page<Profile> page =
				searchRepository.findByObjectiveLikeOrSummaryLikeOrExperienceCompanyLikeOrExperiencePositionLike(
						query, query, query, query, pageable);
		List<Profile> content = page.getContent();
		List<ProfileDTO> dtoList = transformer.transformToList(content, Profile.class, ProfileDTO.class);
		return new PageImpl<>(dtoList, pageable, page.getTotalElements());
	}

//	@Override
//	@Transactional
//	public ProfileDTO create(String firstName, String lastName, String password) {
//		Profile profile = new Profile();
//		profile.setUid(uidGenerator.generateUid(firstName, lastName, uid -> profileRepo.countByUid(uid) > 0));
//		profile.setFirstName(DataUtil.capitalizeName(firstName));
//		profile.setLastName(DataUtil.capitalizeName(lastName));
//		profile.setPassword(passwordEncoder.encode(password));
//		profile.setCompleted(false);
//		
//		Profile savedProfile = profileRepo.save(profile);
//		
//		LOGGER.info("New profile created: {}", profile.getUid());
//		executeIfTransactionSuccess(() -> searchIndexingService.createIndexProfile(savedProfile));
//		
//		return transformer.transform(savedProfile, ProfileDTO.class);
//	}
//	
//	@Override
//	@Transactional
//	public ProfileDTO create(ProfileDTO newProfile) {
//		Profile profile = new Profile();
//		profile.setUid(uidGenerator.generateUid(newProfile.getFirstName(), newProfile.getLastName(), uid -> profileRepo.countByUid(uid) > 0));
//		profile.setFirstName(DataUtil.capitalizeName(newProfile.getFirstName()));
//		profile.setLastName(DataUtil.capitalizeName(newProfile.getLastName()));
//		profile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
//		updateProfileMainInfo(profile, newProfile.getMainInfo());
//		profile.setCompleted(false);
//		
//		Profile savedProfile = profileRepo.save(profile);
//		
//		LOGGER.info("New profile created: {}", profile.getUid());
//		executeIfTransactionSuccess(() -> searchIndexingService.createIndexProfile(savedProfile));
//		
//		return transformer.transform(savedProfile, ProfileDTO.class);
//	}
	
	@Override
	public MainInfoDTO getMainInfoFor(long profileId) {
		return getDTO(profileId, MainInfoDTO.class);
	}
	
	@Override
	@Transactional
	public void updateMainInfo(long profileId, MainInfoDTO updatedMainInfo) {
		Profile profile = profileRepo.findOne(profileId);
		updateProfileMainInfo(profile, updatedMainInfo);
		Profile updatedProfile = profileRepo.save(profile);
		
		executeIfTransactionSuccess(() -> searchIndexingService.createIndexProfile(updatedProfile));
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
		profile.setInfo(info);
		Profile updatedProfile = profileRepo.save(profile);
		
		executeIfTransactionSuccess(() -> searchIndexingService.createIndexProfile(updatedProfile));
	}
	
	@Override
	public ContactsDTO getContactsFor(long profileId) {
		return getDTO(profileId, ContactsDTO.class);
	}
	
	@Override
	@Transactional
	public void updateContacts(long profileId, ContactsDTO updatedContacts) {
		Profile profile = profileRepo.findOne(profileId);
		profile.setContacts(transformer.transform(updatedContacts, Contacts.class));
		profileRepo.save(profile);
	}
	
	@Override
	public List<ExperienceDTO> getExperienceFor(long profileId) {
		return getDTOList(profileId, ExperienceDTO.class);
	}
	
	@Override
	@Transactional
	public void updateExperience(long profileId, List<ExperienceDTO> updatedExperience) {
		updateProfile(profileId, updatedExperience, ExperienceDTO.class, Experience.class, (profile, experiences) -> profile.updateExperience(experiences));
	}
	
	@Override
	public List<CertificateDTO> getCertificatesFor(long profileId) {
		return getDTOList(profileId, CertificateDTO.class);
	}
	
	@Override
	@Transactional
	public void updateCertificates(long profileId, List<CertificateDTO> updatedCertificates) {
		updateProfile(profileId, updatedCertificates, CertificateDTO.class, Certificate.class,
				(profile, certificates) -> {
					profile.updateCertificates(certificates);
					executeIfTransactionSuccess(() -> searchIndexingService.updateIndexProfileCertificates(profileId, certificates));
				});
	}
	
	@Override
	public List<CourseDTO> getCoursesFor(long profileId) {
		return getDTOList(profileId, CourseDTO.class);
	}
	
	@Override
	@Transactional
	public void updateCourses(long profileId, List<CourseDTO> updatedCourses) {
		updateProfile(profileId, updatedCourses, CourseDTO.class, Course.class,
				(profile, courses) -> {
					profile.updateCourses(courses);
					executeIfTransactionSuccess(() -> searchIndexingService.updateIndexProfileCourses(profileId, courses));
				});
	}
	
	@Override
	public List<EducationDTO> getEducationFor(long profileId) {
		return getDTOList(profileId, EducationDTO.class);
	}
	
	@Override
	@Transactional
	public void updateEducation(long profileId, List<EducationDTO> updatedEducation) {
		updateProfile(profileId, updatedEducation, EducationDTO.class, Education.class, (profile, educations) -> profile.updateEducation(educations));
	}
	
	@Override
	public List<LanguageDTO> getLanguagesFor(long profileId) {
		return getDTOList(profileId, LanguageDTO.class);
	}
	
	@Override
	@Transactional
	public void updateLanguages(long profileId, List<LanguageDTO> updatedLanguages) {
		updateProfile(profileId, updatedLanguages, LanguageDTO.class, Language.class,
				(profile, langs) -> {
					profile.updateLanguages(langs);
					executeIfTransactionSuccess(() -> searchIndexingService.updateIndexProfileLanguages(profileId, langs));
				});
	}
	
	@Override
	public List<HobbyDTO> getHobbiesFor(long profileId) {
		return getDTOList(profileId, HobbyDTO.class);
	}
	
	@Override
	@Transactional
	public void updateHobbies(long profileId, List<HobbyDTO> updatedHobbies) {
		updateProfile(profileId, updatedHobbies, HobbyDTO.class, Hobby.class, (profile, hobbies) -> profile.updateHobbies(hobbies));
	}

	@Override
	public List<SkillDTO> getSkillsFor(long profileId) {
		return getDTOList(profileId, SkillDTO.class);
	}
	
	@Override
	@Transactional
	public void updateSkills(long profileId, List<SkillDTO> updatedSkills) {
		updateProfile(profileId, updatedSkills, SkillDTO.class, Skill.class,
				(profile, skills) -> {
					profile.updateSkills(skills);
					executeIfTransactionSuccess(() -> searchIndexingService.updateIndexProfileSkills(profileId, skills));
				});
	}

	@Override
	public List<String> getAllSkillCategories() {
		List<String> skillCategories = new ArrayList<>();
		for (SkillCategory category : SkillCategory.values()) {
			skillCategories.add(category.getCategory());
		}
		return skillCategories;
	}
	
	@Override
	public List<String> getLanguageLevels() {
		List<String> languageTypes = new ArrayList<>();
		for (LanguageType type : LanguageType.values()) {
			languageTypes.add(type.getType());
		}
		return languageTypes;
	}
	
	@Override
	public List<String> getLanguageTypes() {
		List<String> languageLevels = new ArrayList<>();
		for (LanguageLevel level : LanguageLevel.values()) {
			languageLevels.add(level.getLevel());
		}
		return languageLevels;
	}
	
	@FunctionalInterface
	private interface ProfileUpdate<V> {
		void update(Profile p, V value);
	}
	
	private <T> T getDTO(long profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transform(profile, dtoClass);
	}
	
	private <T> List<T> getDTOList(long profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transformToList(profile, dtoClass);
	}
	
	private <T,E> void updateProfile(long profileId, List<T> updatedDTOList, Class<T> dtoClass, Class<E> entityClass,
			ProfileUpdate<List<E>> profileUpdate) {
		Profile profile = profileRepo.findOne(profileId);
		List<T> profileDTOList = transformer.transformToList(profile, dtoClass);
		if (CollectionUtils.isEqualCollection(updatedDTOList, profileDTOList)) {
			LOGGER.debug("{} for profile with id:{} - nothing to update", entityClass.getSimpleName(), profileId);
		} else {
			List<E> updatedEntityList = transformer.transformToList(updatedDTOList, dtoClass, entityClass);
			profileUpdate.update(profile, updatedEntityList);
			profileRepo.save(profile);
		}
	}
	
	private void executeIfTransactionSuccess(Runnable action) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				action.run();
			}
		});
	}

	private void updateProfileMainInfo(Profile profile, MainInfoDTO mainInfo) {
		profile.setLargePhoto(mainInfo.getLargePhoto());
		profile.setSmallPhoto(mainInfo.getSmallPhoto());
		profile.setBirthDay(mainInfo.getBirthDate());
		profile.setCountry(mainInfo.getCountry());
		profile.setCity(mainInfo.getCity());
		profile.setEmail(mainInfo.getEmail());
		profile.setPhone(mainInfo.getPhone());
		profile.setObjective(mainInfo.getObjective());
		profile.setSummary(mainInfo.getSummary());
	}
}
