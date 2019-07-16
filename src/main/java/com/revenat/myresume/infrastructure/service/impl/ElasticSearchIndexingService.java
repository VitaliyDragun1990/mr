package com.revenat.myresume.infrastructure.service.impl;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.domain.document.Certificate;
import com.revenat.myresume.domain.document.Course;
import com.revenat.myresume.domain.document.Language;
import com.revenat.myresume.domain.document.PracticalExperience;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.domain.document.ProfileDocument;
import com.revenat.myresume.domain.document.Skill;
import com.revenat.myresume.infrastructure.repository.search.ProfileSearchRepository;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.Checks;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Service
class ElasticSearchIndexingService implements SearchIndexingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchIndexingService.class);

	private final boolean indexAllDuringStartup;
	private final ProfileSearchRepository searchRepo;
	private final ProfileRepository profileRepo;
	private final ElasticsearchOperations elasticsearchOperations;
	
	private Map<Class<? extends ProfileDocument>, ProfileDataIndexUpdater> updaterRegistry;

	@Autowired
	public ElasticSearchIndexingService(
			@Value("${index.all.during.startup}") boolean indexAllDuringStartup,
			ProfileSearchRepository searchRepo, ProfileRepository profileRepo,
			ElasticsearchOperations elasticsearchOperations) {
		this.indexAllDuringStartup = indexAllDuringStartup;
		this.searchRepo = searchRepo;
		this.profileRepo = profileRepo;
		this.elasticsearchOperations = elasticsearchOperations;
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	private void populateUpdaterRegistry() {
		Map<Class<? extends ProfileDocument>, ProfileDataIndexUpdater> map = new HashMap<>();
		map.put(Skill.class, (id, skills) -> updateIndexProfileSkills(id, (List<Skill>) skills));
		map.put(Certificate.class, (id, certificates) -> updateIndexProfileCertificates(id, (List<Certificate>) certificates));
		map.put(Course.class, (id, courses) -> updateIndexProfileCourses(id, (List<Course>) courses));
		map.put(PracticalExperience.class, (id, experience) -> updateIndexProfileExperience(id, (List<PracticalExperience>) experience));
		map.put(Language.class, (id, languages) -> updateIndexProfileLanguages(id, (List<Language>) languages));
		
		updaterRegistry = CommonUtils.getSafeMap(map);
	}

	@Override
	public void createNewProfileIndex(Profile profile) {
		Checks.checkParam(profile != null, "Profile to create new index for can not be null");
		
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
	public void removeProfileIndex(Profile profile) {
		Checks.checkParam(profile != null, "Profile to remove index for can not be null");
		
		searchRepo.delete(profile);
		LOGGER.info("Profile index deleted: {}", profile.getUid());
	}

	@Override
	public <T extends Annotation> void updateProfileIndex(Profile updatedProfile, Class<T> dataTypeAnnotationClass) {
		Checks.checkParam(updatedProfile != null, "Profile to update index for can not be null");
		Checks.checkParam(dataTypeAnnotationClass != null, "Annotation which designates which profile data should be updated "
				+ "can not be null");
		
		Profile profileToUpdate = searchRepo.findOne(updatedProfile.getId());
		if (profileToUpdate == null) {
			createNewProfileIndex(updatedProfile);
		} else {
			ReflectionUtil.copyFields(updatedProfile, profileToUpdate, dataTypeAnnotationClass);
			if (CommonUtils.isNotBlank(updatedProfile.getLargePhoto()) || CommonUtils.isNotBlank(updatedProfile.getSmallPhoto())) {
				profileToUpdate.setLargePhoto(updatedProfile.getLargePhoto());
				profileToUpdate.setSmallPhoto(updatedProfile.getSmallPhoto());
			}

			searchRepo.save(profileToUpdate);
			LOGGER.info("Data for profile with uid: {} has been updated", profileToUpdate.getUid());
		}
	}
	
	@Override
	public <E extends ProfileDocument> void updateProfileAggregateDataIndex(String profileId, List<E> updatedData,
			Class<E> profileAggregateDataClass) {
		Checks.checkParam(profileId != null, "index for profile to update index for can not be null");
		Checks.checkParam(updatedData != null, "list with updated profile data can not be null");
		Checks.checkParam(profileAggregateDataClass != null, "class of profile data to update can not be null");
		
		ProfileDataIndexUpdater indexUpdater = updaterRegistry.get(profileAggregateDataClass);
		if (indexUpdater != null) {
			indexUpdater.updateProfileDataIndex(profileId, updatedData);
		}
	}

	protected void updateIndexProfileSkills(String profileId, List<Skill> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setSkills(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Skills for profile with uid: {} have been updated", profile.getUid());
	}

	protected void updateIndexProfileCertificates(String profileId, List<Certificate> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setCertificates(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Certificates for profile with uid: {} have been updated", profile.getUid());
	}

	protected void updateIndexProfileCourses(String profileId, List<Course> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setCourses(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Courses for profile with uid: {} have been updated", profile.getUid());
	}

	protected void updateIndexProfileExperience(String profileId, List<PracticalExperience> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setExperience(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Practical experience for profile with uid: {} has been updated", profile.getUid());
	}

	protected void updateIndexProfileLanguages(String profileId, List<Language> updatedData) {
		Profile profile = searchRepo.findOne(profileId);
		profile.setLanguages(updatedData);
		searchRepo.save(profile);
		LOGGER.info("Languages for profile with uid: {} have been updated", profile.getUid());
	}

	@PostConstruct
	protected void buildSearchIndex() {
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

	protected Iterable<Profile> findAllForIndexing() {
		return profileRepo.findAll();
	}
	
	@FunctionalInterface
	interface ProfileDataIndexUpdater {
		void updateProfileDataIndex(String profileId, List<? extends ProfileDocument> updatedData);
	}

}
