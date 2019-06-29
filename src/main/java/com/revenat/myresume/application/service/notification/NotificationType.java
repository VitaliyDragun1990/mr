package com.revenat.myresume.application.service.notification;

/**
 * Represents types of the notifications which can be send from application.
 */
public enum NotificationType {

	RESTORE_ACCESS("restoreAccessNotification"),
	PASSWORD_CHANGED("passwordChangedNotification"),
	PASSWORD_GENERATED("passwordGeneratedNotification");
	
	private final String notificationName;

	private NotificationType(String notificationName) {
		this.notificationName = notificationName;
	}
	
	public String getNotificationName() {
		return notificationName;
	}
}
