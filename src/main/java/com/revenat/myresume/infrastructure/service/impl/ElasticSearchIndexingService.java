package com.revenat.myresume.infrastructure.service.impl;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.PracticalExperience;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.ProfileEntity;
import com.revenat.myresume.domain.entity.Skill;
import com.revenat.myresume.infrastructure.repository.search.ProfileSearchRepository;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Service
class ElasticSearchIndexingService implements SearchIndexingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchIndexingService.class);

	private final boolean indexAllDuringStartup;
	private final ProfileSearchRepository searchRepo;
	private final ProfileRepository profileRepo;
	private final ElasticsearchOperations elasticsearchOperations;
	private final PlatformTransactionManager txManager;
	
	private Map<Class<? extends ProfileEntity>, ProfileEntitiesIndexUpdater> updaterRegistry;

	@Autowired
	public ElasticSearchIndexingService(
			@Value("${index.all.during.startup}") boolean indexAllDuringStartup,
			ProfileSearchRepository searchRepo, ProfileRepository profileRepo,
			ElasticsearchOperations elasticsearchOperations,
			@Qualifier("transactionManager") PlatformTransactionManager txManager) {
		this.indexAllDuringStartup = indexAllDuringStartup;
		this.searchRepo = searchRepo;
		this.profileRepo = profileRepo;
		this.elasticsearchOperations = elasticsearchOperations;
		this.txManager = txManager;
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	private void populateUpdaterRegistry() {
		Map<Class<? extends ProfileEntity>, ProfileEntitiesIndexUpdater> map = new HashMap<>();
		map.put(Skill.class, (id, skills) -> updateIndexProfileSkills(id, (List<Skill>) skills));
		map.put(Certificate.class, (id, certificates) -> updateIndexProfileCertificates(id, (List<Certificate>) certificates));
		map.put(Course.class, (id, courses) -> updateIndexProfileCourses(id, (List<Course>) courses));
		map.put(PracticalExperience.class, (id, experience) -> updateIndexProfileExperience(id, (List<PracticalExperience>) experience));
		map.put(Language.class, (id, languages) -> updateIndexProfileLanguages(id, (List<Language>) languages));
	}

	@Override
	public void createNewProfileIndex(Profile profile) {
		if (CommonUtils.isNullOrEmpty(profile.getCertificates())) {
			profile.setCertificates(Collections.emptyList());
		}
		if (CommonUtils.isNullOrEmpty(profile.getExperience())) {
			profile.setExperience(Collections.emptyList());
		}
		if (CommonUtils.isNullOrEmpty(profile.getLanguages())) {
			profile.setLanguages(Collections.emptyList());
		}
		if (CommonUtils.isNullOrEmpty(profile.getSkills())) {
			profile.setSkills(Collections.emptyList());
		}
		if (CommonUtils.isNullOrEmpty(profile.getCourses())) {
			profile.setCourses(Collections.emptyList());
		}
		
		searchRepo.save(profile);
		LOGGER.info("New profile index created: {}", profile.getUid());
	}
	
	@Override
	public void removeIndexProfile(Profile profile) {
		searchRepo.delete(profile);
		LOGGER.info("Profile index deleted: {}", profile.getUid());
	}

	@Override
	public <T extends Annotation> void updateProfileIndex(Profile profile, Class<T> annotaionClass) {
		Profile p = searchRepo.findOne(profile.getId());
		if (p == null) {
			createNewProfileIndex(profile);
		} else {
			ReflectionUtil.copyFields(profile, p, annotaionClass);
			if (CommonUtils.isNotBlank(profile.getLargePhoto()) || CommonUtils.isNotBlank(profile.getSmallPhoto())) {
				p.setLargePhoto(profile.getLargePhoto());
				p.setSmallPhoto(profile.getSmallPhoto());
			}

			searchRepo.save(p);
			LOGGER.info("Data for profile with uid: {} has been updated", p.getUid());
		}
	}
	
	@Override
	public <E extends ProfileEntity> void updateProfileEntitiesIndex(long profileId, List<E> updatedData,
			Class<E> profileEntityClass) {
		updaterRegistry.get(profileEntityClass).updateProfileEntitiesIndex(profileId, updatedData);
	}

	void updateIndexProfileSkills(long profileId, List<Skill> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setSkills(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Skills for profile with uid: {} have been updated", profile.getUid());
	}

	void updateIndexProfileCertificates(long profileId, List<Certificate> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setCertificates(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Certificates for profile with uid: {} have been updated", profile.getUid());
	}

	void updateIndexProfileCourses(long profileId, List<Course> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setCourses(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Courses for profile with uid: {} have been updated", profile.getUid());
	}

	void updateIndexProfileExperience(long profileId, List<PracticalExperience> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setExperience(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Practical experience for profile with uid: {} has been updated", profile.getUid());
	}

	void updateIndexProfileLanguages(long profileId, List<Language> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setLanguages(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Languages for profile with uid: {} have been updated", profile.getUid());
	}

	@PostConstruct
	void buildSearchIndex() {
		if (indexAllDuringStartup) {
			LOGGER.info("Detected index all command");
			LOGGER.info("Clear old index");
			elasticsearchOperations.deleteIndex(Profile.class);
			LOGGER.info("Start index of profiles");
			for (Profile p : findAllForIndexing()) {
				searchRepo.save(p);
				LOGGER.info("Successfully indexed profile: {}", p.getUid());
			}
			LOGGER.info("Finished building index of all profiles");
		} else {
			LOGGER.info("indexAllDuringStartup is disabled");
		}
	}

	Iterable<Profile> findAllForIndexing() {
		TransactionTemplate tmpl = new TransactionTemplate(txManager);
		return tmpl.execute(status -> {
			Iterable<Profile> all = profileRepo.findAll();
			// Deal with lazy loading
			for (Profile p : all) {
				Hibernate.initialize(p.getSkills());
				Hibernate.initialize(p.getCertificates());
				Hibernate.initialize(p.getLanguages());
				Hibernate.initialize(p.getExperience());
				Hibernate.initialize(p.getCourses());
			}
			return all;
		});
	}
	
	@FunctionalInterface
	interface ProfileEntitiesIndexUpdater {
		void updateProfileEntitiesIndex(long profileId, List<? extends ProfileEntity> updatedData);
	}

}
