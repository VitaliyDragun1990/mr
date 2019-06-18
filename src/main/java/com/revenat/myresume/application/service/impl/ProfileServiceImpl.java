package com.revenat.myresume.application.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.revenat.myresume.application.generator.UidGenerator;
import com.revenat.myresume.application.service.ProfileService;
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
import com.revenat.myresume.infrastructure.repository.ProfileRepository;

@Service
class ProfileServiceImpl implements ProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);

	private final ProfileRepository profileRepo;
	private final Transformer transformer;
	private final UidGenerator uidGenerator;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepo, Transformer transformer, UidGenerator uidGenerator) {
		this.profileRepo = profileRepo;
		this.transformer = transformer;
		this.uidGenerator = uidGenerator;
	}

	@Override
	@Transactional
	public ProfileDTO create(String firstName, String lastName, String password) {
		Profile profile = new Profile();
		profile.setUid(uidGenerator.generateUid(firstName, lastName, uid -> profileRepo.countByUid(uid) > 0));
		profile.setFirstName(DataUtil.capitalizeName(firstName));
		profile.setLastName(DataUtil.capitalizeName(lastName));
		profile.setPassword(password);
		profile.setCompleted(false);
		
		profile = profileRepo.save(profile);
		
		return transformer.transform(profile, ProfileDTO.class);
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
	public List<ProfileDTO> get(int page, int limit) {
		Pageable sortByName = new PageRequest(page - 1, limit, Direction.ASC, "firstName", "lastName");
		Page<Profile> profilesPage = profileRepo.findAll(sortByName);
		List<Profile> content = profilesPage.getContent();
		return transformer.transformToList(content, Profile.class, ProfileDTO.class);
	}
	
	@Override
	public MainInfoDTO getMainInfoFor(long profileId) {
		return getDTO(profileId, MainInfoDTO.class);
	}
	
	@Override
	@Transactional
	public void updateMainInfo(long profileId, MainInfoDTO updatedMainInfo) {
		Profile profile = profileRepo.findOne(profileId);
		updateProfileMainInfo(profile, updatedMainInfo);
		profileRepo.save(profile);
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
		Profile profile = profileRepo.findOne(profileId);
		List<ExperienceDTO> profileExperience = transformer.transformToList(profile, ExperienceDTO.class);
		if (CollectionUtils.isEqualCollection(updatedExperience, profileExperience)) {
			LOGGER.debug("Experience for profile with id:{} - nothing to update", profileId);
		} else {
			profile.updateExperience(transformer.transformToList(updatedExperience, ExperienceDTO.class, Experience.class));
			profileRepo.save(profile);
		}
	}
	
	@Override
	public List<CertificateDTO> getCertificatesFor(long profileId) {
		return getDTOList(profileId, CertificateDTO.class);
	}
	
	@Override
	@Transactional
	public void updateCertificates(long profileId, List<CertificateDTO> updatedCertificates) {
		Profile profile = profileRepo.findOne(profileId);
		List<CertificateDTO> profileCertificates = transformer.transformToList(profile, CertificateDTO.class);
		if (CollectionUtils.isEqualCollection(updatedCertificates, profileCertificates)) {
			LOGGER.debug("Certificates for profile with id:{} - nothing to update", profileId);
		} else {
			profile.updateCertificates(transformer.transformToList(updatedCertificates, CertificateDTO.class, Certificate.class));
			profileRepo.save(profile);
		}
	}
	
	@Override
	public List<CourseDTO> getCoursesFor(long profileId) {
		return getDTOList(profileId, CourseDTO.class);
	}
	
	@Override
	@Transactional
	public void updateCourses(long profileId, List<CourseDTO> updatedCourses) {
		Profile profile = profileRepo.findOne(profileId);
		List<CourseDTO> profileCourses = transformer.transformToList(profile, CourseDTO.class);
		if (CollectionUtils.isEqualCollection(updatedCourses, profileCourses)) {
			LOGGER.debug("Courses for profile with id:{} - nothing to update", profileId);
		} else {
			profile.updateCourses(transformer.transformToList(updatedCourses, CourseDTO.class, Course.class));
			profileRepo.save(profile);
		}
	}
	
	@Override
	public List<EducationDTO> getEducationFor(long profileId) {
		return getDTOList(profileId, EducationDTO.class);
	}
	
	@Override
	@Transactional
	public void updateEducation(long profileId, List<EducationDTO> updatedEducation) {
		Profile profile = profileRepo.findOne(profileId);
		List<EducationDTO> profileEducation = transformer.transformToList(profile, EducationDTO.class);
		if (CollectionUtils.isEqualCollection(updatedEducation, profileEducation)) {
			LOGGER.debug("Education for profile with id:{} - nothing to update", profileId);
		} else {
			profile.updateEducation(transformer.transformToList(updatedEducation, EducationDTO.class, Education.class));
			profileRepo.save(profile);
		}
	}
	
	@Override
	public List<LanguageDTO> getLanguagesFor(long profileId) {
		return getDTOList(profileId, LanguageDTO.class);
	}
	
	@Override
	@Transactional
	public void updateLanguages(long profileId, List<LanguageDTO> updatedLanguages) {
		Profile profile = profileRepo.findOne(profileId);
		List<LanguageDTO> profileLanguages = transformer.transformToList(profile, LanguageDTO.class);
		if (CollectionUtils.isEqualCollection(updatedLanguages, profileLanguages)) {
			LOGGER.debug("Foreign languages for profile with id:{} - nothing to update", profileId);
		} else {
			profile.updateLanguages(transformer.transformToList(updatedLanguages, LanguageDTO.class, Language.class));
			profileRepo.save(profile);
		}
	}
	
	@Override
	public List<HobbyDTO> getHobbiesFor(long profileId) {
		return getDTOList(profileId, HobbyDTO.class);
	}
	
	@Override
	@Transactional
	public void updateHobbies(long profileId, List<HobbyDTO> updatedHobbies) {
		Profile profile = profileRepo.findOne(profileId);
		List<HobbyDTO> profileHobbies = transformer.transformToList(profile, HobbyDTO.class);
		if (CollectionUtils.isEqualCollection(updatedHobbies, profileHobbies)) {
			LOGGER.debug("Foreign languages for profile with id:{} - nothing to update", profileId);
		} else {
			profile.updateHobbies(transformer.transformToList(updatedHobbies, HobbyDTO.class, Hobby.class));
			profileRepo.save(profile);
		}
	}

	@Override
	public List<SkillDTO> getSkillsFor(long profileId) {
		return getDTOList(profileId, SkillDTO.class);
	}
	
	@Override
	@Transactional
	public void updateSkills(long profileId, List<SkillDTO> updatedSkills) {
		Profile profile = profileRepo.findOne(profileId);
		List<SkillDTO> profileSkills = transformer.transformToList(profile, SkillDTO.class);
		if (CollectionUtils.isEqualCollection(updatedSkills, profileSkills)) {
			LOGGER.debug("Skills for profile with id:{} - nothing to update", profileId);
		} else {
			profile.updateSkills(transformer.transformToList(updatedSkills, SkillDTO.class, Skill.class));
			profileRepo.save(profile);
		}
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
		profileRepo.save(profile);
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
	
	private <T> T getDTO(long profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transform(profile, dtoClass);
	}
	
	private <T> List<T> getDTOList(long profileId, Class<T> dtoClass) {
		Profile profile = profileRepo.findOne(profileId);
		return transformer.transformToList(profile, dtoClass);
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
