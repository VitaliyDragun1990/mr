package com.revenat.myresume.application.service.task.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revenat.myresume.application.provider.DateTimeProvider;
import com.revenat.myresume.application.service.task.TaskPerformer;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;

@Service
class ScheduledTaskPerformer implements TaskPerformer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTaskPerformer.class);
	
	private final ProfileRepository profileRepo;
	private final DateTimeProvider dateTimeProvider;
	private final int removeNotCompletedProfilesInterval;
	
	@Autowired
	public ScheduledTaskPerformer(
			ProfileRepository profileRepo,
			DateTimeProvider dateTimeProvider,
			@Value("${remove.not.completed.profiles.interval}") int removeNotCompletedProfilesInterval) {
		this.profileRepo = profileRepo;
		this.dateTimeProvider = dateTimeProvider;
		this.removeNotCompletedProfilesInterval = removeNotCompletedProfilesInterval;
	}


	@Transactional
	@Scheduled(cron = "0 59 23 * * *") // every day at 23:59:00
	@Override
	public void removeNotCompletedProfiles() {
		LocalDateTime date = dateTimeProvider.getCurrentDateTime().minusDays(removeNotCompletedProfilesInterval);
		List<Long> idsToRemove = new ArrayList<>();
		for (Profile profile : profileRepo.findByCompletedFalseAndCreatedBefore(date)) {
			idsToRemove.add(profile.getId());
			profileRepo.delete(profile);
		}
		LOGGER.info("Removed not completed profiles with ids: {}", idsToRemove);
	}

}
