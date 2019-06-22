package com.revenat.myresume.application.service.notification.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.exception.CantCompleteClientRequestException;
import com.revenat.myresume.application.model.NotificationMessage;
import com.revenat.myresume.application.service.notification.NotificationBuilderService;
import com.revenat.myresume.application.service.notification.NotificationType;
import com.revenat.myresume.application.template.TemplateContentResolver;

/**
 * Builds {@link NotificationMessage} from some kind of template
 * 
 * @author Vitaliy Dragun
 *
 */
@Service
class TemplateNotificationBuilderService implements NotificationBuilderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateNotificationBuilderService.class);
	
	private final Map<String, NotificationMessage> notificationTemplates;
	private final TemplateContentResolver contentResolver;
	
	@Autowired
	public TemplateNotificationBuilderService(Map<String, NotificationMessage> notificationTemplates,
			TemplateContentResolver contentResolver) {
		this.notificationTemplates = notificationTemplates;
		this.contentResolver = contentResolver;
	}

	@PostConstruct
	private void postConstruct() {
		LOGGER.info("Loaded {} notification templates", notificationTemplates.size());
	}

	/**
	 * Builds {@link NotificationMessage} using template with provided
	 * {@code templateName} and {@code model}
	 * 
	 * @author Vitaliy Dragun
	 *
	 */
	@Override
	public NotificationMessage createNotificationMessage(NotificationType notificationType, Map<String, Object> model) {
		String templateName = notificationType.getNotificationName();
		NotificationMessage message = notificationTemplates.get(templateName);
		if (message == null) {
			throw new CantCompleteClientRequestException("Notification template '" + templateName + "' not found");
		}
		String resolvedSubject = contentResolver.resolve(message.getSubject(), model);
		String resolvedContent = contentResolver.resolve(message.getContent(), model);
		return new NotificationMessage(resolvedSubject, resolvedContent);
	}

}
