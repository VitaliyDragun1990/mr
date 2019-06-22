package com.revenat.myresume.application.service.notification;

import java.util.Map;

import com.revenat.myresume.application.model.NotificationMessage;

/**
 * Builds {@link NotificationMessage}.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface NotificationBuilderService {

	/**
	 * Creates {@link NotificationMessage} instance.
	 * 
	 * @param notificationType type of the notification message to create
	 * @param model            map with additional arguments which may be needed to
	 *                         build notification messsage
	 * @return {@link NotificationMessage} instance
	 */
	NotificationMessage createNotificationMessage(NotificationType notificationType, Map<String, Object> model);
}
