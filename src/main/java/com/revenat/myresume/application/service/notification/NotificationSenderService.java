package com.revenat.myresume.application.service.notification;

import javax.annotation.Nonnull;

import com.revenat.myresume.domain.entity.Profile;

/**
 * Sends {@link NotificationMessage} via some kind of transport protocol.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface NotificationSenderService {

	void sendNotification(@Nonnull NotificationMessage message);

	String getDestinationAddress(@Nonnull Profile profile);
}
