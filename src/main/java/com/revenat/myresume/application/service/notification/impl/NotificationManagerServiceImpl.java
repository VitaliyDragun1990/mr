package com.revenat.myresume.application.service.notification.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.model.NotificationMessage;
import com.revenat.myresume.application.service.notification.NotificationBuilderService;
import com.revenat.myresume.application.service.notification.NotificationManagerService;
import com.revenat.myresume.application.service.notification.NotificationSenderService;
import com.revenat.myresume.application.service.notification.NotificationType;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Service
class NotificationManagerServiceImpl implements NotificationManagerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationManagerServiceImpl.class);
	
	private final NotificationSenderService notificationSenderService;
	private final NotificationBuilderService notificationBuilderService;
	
	@Autowired
	public NotificationManagerServiceImpl(NotificationSenderService senderService,
			NotificationBuilderService builderService) {
		this.notificationSenderService = senderService;
		this.notificationBuilderService = builderService;
	}

	@Override
	public void sendRestoreAccessLink(Profile profile, String restoreLink) {
		LOGGER.debug("Restore link: {} for account {}", restoreLink, profile.getUid());
		Map<String, Object> model = new HashMap<>();
		model.put("profile", profile);
		model.put("restoreLink", restoreLink);
		processNotification(profile, NotificationType.RESTORE_ACCESS, model);
	}

	@Override
	public void sendPasswordChanged(Profile profile) {
		LOGGER.debug("Password changed for account {}", profile.getUid());
		Map<String, Object> model = new HashMap<>();
		model.put("profile", profile);
		processNotification(profile, NotificationType.PASSWORD_CHANGED, model);
	}
	
	@Override
	public void sendPasswordGenerated(Profile profile, String generatedPassword) {
		LOGGER.debug("Password generated for account {}", profile.getUid());
		Map<String, Object> model = new HashMap<>();
		model.put("profile", profile);
		model.put("generatedPassword", generatedPassword);
		processNotification(profile, NotificationType.PASSWORD_GENERATED, model);
	}
	
	private void processNotification(Profile profile, NotificationType notificationType, Map<String, Object> model) {
		String destinationAddress = notificationSenderService.getDestinationAddress(profile);
		if (CommonUtils.isNotBlank(destinationAddress)) {
			NotificationMessage notificationMessage = notificationBuilderService.createNotificationMessage(notificationType, model);
			notificationMessage.setDestinationAddress(destinationAddress);
			notificationMessage.setDestinationName(getFullName(profile));
			
			notificationSenderService.sendNotification(notificationMessage);
		} else {
			LOGGER.error("Notification ignored: destinationAddress is empty for profile: {}", profile.getUid());
		}
		
	}

	private String getFullName(Profile profile) {
		return profile.getFirstName() + " " + profile.getLastName();
	}

}
