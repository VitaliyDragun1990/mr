package com.revenat.myresume.application.service.notification;

import java.util.Map;

import javax.annotation.Nonnull;

/**
 * Builds {@link NotificationMessage}.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface NotificationBuilderService {

	/**
	 * Builds {@link NotificationMessage} instance.
	 * 
	 * @param notificationType type of the notification message to create
	 * @param model            map with additional arguments which may be needed to
	 *                         build notification messsage
	 * @return {@link NotificationMessage} instance
	 */
	NotificationMessage buildNotificationMessage(@Nonnull NotificationType notificationType, @Nonnull Map<String, Object> model);
}
