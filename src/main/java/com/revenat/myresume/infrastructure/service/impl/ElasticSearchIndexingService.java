package com.revenat.myresume.infrastructure.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Experience;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.Skill;
import com.revenat.myresume.infrastructure.repository.search.ProfileSearchRepository;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;

@Service
class ElasticSearchIndexingService implements SearchIndexingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchIndexingService.class);

	private final boolean indexAllDuringStartup;
	private final ProfileSearchRepository searchRepo;
	private final ProfileRepository profileRepo;
	private final ElasticsearchOperations elasticsearchOperations;
	private final PlatformTransactionManager txManager;

	@Autowired
	public ElasticSearchIndexingService(@Value("${index.all.during.startup}") boolean indexAllDuringStartup,
			ProfileSearchRepository searchRepo, ProfileRepository profileRepo,
			ElasticsearchOperations elasticsearchOperations,
			@Qualifier("transactionManager") PlatformTransactionManager txManager) {
		this.indexAllDuringStartup = indexAllDuringStartup;
		this.searchRepo = searchRepo;
		this.profileRepo = profileRepo;
		this.elasticsearchOperations = elasticsearchOperations;
		this.txManager = txManager;
	}

	@Override
	public void createIndexProfile(Profile profile) {
		profile.setCertificates(Collections.emptyList());
		profile.setExperience(Collections.emptyList());
		profile.setLanguages(Collections.emptyList());
		profile.setSkills(Collections.emptyList());
		profile.setCourses(Collections.emptyList());
		
		searchRepo.save(profile);
		LOGGER.info("New profile index created: {}", profile.getUid());

	}

	@Override
	public void updateIndexProfile(Profile profile) {
		Profile p = searchRepo.findOne(profile.getId());
		p.setBirthDay(profile.getBirthDay());
		p.setCountry(profile.getCountry());
		p.setCity(profile.getCity());
		p.setObjective(profile.getObjective());
		p.setSummary(profile.getSummary());
		p.setSmallPhoto(profile.getSmallPhoto());
		p.setInfo(profile.getInfo());
		
		searchRepo.save(p);
		LOGGER.info("Main info for profile with uid: {} has been updated", p.getUid());
	}

	@Override
	public void updateIndexProfileSkills(long profileId, List<Skill> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setSkills(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Skills for profile with uid: {} have been updated", profile.getUid());
	}

	@Override
	public void updateIndexProfileCertificates(long profileId, List<Certificate> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setCertificates(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Certificates for profile with uid: {} have been updated", profile.getUid());
	}

	@Override
	public void updateIndexProfileCourses(long profileId, List<Course> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setCourses(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Courses for profile with uid: {} have been updated", profile.getUid());
	}

	@Override
	public void updateIndexProfileExperience(long profileId, List<Experience> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setExperience(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Practical experience for profile with uid: {} has been updated", profile.getUid());
	}

	@Override
	public void updateIndexProfileLanguages(long profileId, List<Language> updatedData) {
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
			for (Profile p : all) {
				p.getSkills().size();
				p.getCertificates().size();
				p.getLanguages().size();
				p.getExperience().size();
				p.getCourses().size();
			}
			return all;
		});
	}

}
