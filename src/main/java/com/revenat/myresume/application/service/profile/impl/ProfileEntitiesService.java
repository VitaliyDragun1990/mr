package com.revenat.myresume.application.service.profile.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Education;
import com.revenat.myresume.domain.entity.Hobby;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.PracticalExperience;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.ProfileEntity;
import com.revenat.myresume.domain.entity.Skill;
import com.revenat.myresume.infrastructure.repository.storage.AbstractProfileEntityRepository;
import com.revenat.myresume.infrastructure.repository.storage.CertificateRepository;
import com.revenat.myresume.infrastructure.repository.storage.CourseRepository;
import com.revenat.myresume.infrastructure.repository.storage.EducationRepository;
import com.revenat.myresume.infrastructure.repository.storage.HobbyRepository;
import com.revenat.myresume.infrastructure.repository.storage.LanguageRepository;
import com.revenat.myresume.infrastructure.repository.storage.PracticalExperienceRepository;
import com.revenat.myresume.infrastructure.repository.storage.SkillRepository;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
class ProfileEntitiesService {

	private final CertificateRepository certificateRepository;
	private final CourseRepository courseRepository;
	private final EducationRepository educationRepository;
	private final HobbyRepository hobbyRepository;
	private final LanguageRepository languageRepository;
	private final PracticalExperienceRepository experienceRepository;
	private final SkillRepository skillRepository;
	
	private Map<Class<? extends ProfileEntity>, AbstractProfileEntityRepository<? extends ProfileEntity>> profileEntityRepoMap;

	@Autowired
	public ProfileEntitiesService(CertificateRepository certificateRepository, CourseRepository courseRepository,
			EducationRepository educationRepository, HobbyRepository hobbyRepository,
			LanguageRepository languageRepository, PracticalExperienceRepository experienceRepository,
			SkillRepository skillRepository) {
		this.certificateRepository = certificateRepository;
		this.courseRepository = courseRepository;
		this.educationRepository = educationRepository;
		this.hobbyRepository = hobbyRepository;
		this.languageRepository = languageRepository;
		this.experienceRepository = experienceRepository;
		this.skillRepository = skillRepository;
	}
	
	@PostConstruct
	private void buildProfileEntityrepoMap() {
		Map<Class<? extends ProfileEntity>, AbstractProfileEntityRepository<? extends ProfileEntity>> map = new HashMap<>();
		map.put(Certificate.class, certificateRepository);
		map.put(Course.class, courseRepository);
		map.put(Education.class, educationRepository);
		map.put(Hobby.class, hobbyRepository);
		map.put(Language.class, languageRepository);
		map.put(PracticalExperience.class, experienceRepository);
		map.put(Skill.class, skillRepository);
		
		profileEntityRepoMap = CommonUtils.getSafeMap(map);
	}
	
	public <E extends ProfileEntity> List<E> findByProfileOrderByIdAsc(long profileId, Class<E> entityClass) {
		AbstractProfileEntityRepository<E> entityRepo = findUpdatedDataRepository(entityClass);
		return entityRepo.findByProfileIdOrderByIdAsc(profileId);
	}
	
	@Transactional
	public <E extends ProfileEntity> void updateProfile(Profile profile, List<E> updatedData, Class<E> updatedDataClass) {
		AbstractProfileEntityRepository<E> updatedDataRepo = findUpdatedDataRepository(updatedDataClass);
		updatedDataRepo.deleteByProfileId(profile.getId());
		updatedDataRepo.flush();
		for (E entity : updatedData) {
			entity.setId(null);
			entity.setProfile(profile);
			updatedDataRepo.saveAndFlush(entity);
		}
	}

	@SuppressWarnings("unchecked")
	private <E extends ProfileEntity> AbstractProfileEntityRepository<E> findUpdatedDataRepository(
			Class<? extends ProfileEntity> updatedDataClass) {
		return (AbstractProfileEntityRepository<E>) profileEntityRepoMap.get(updatedDataClass);
	}

}
