package com.revenat.myresume.application.service.notification;

import com.revenat.myresume.application.model.NotificationMessage;
import com.revenat.myresume.domain.entity.Profile;

/**
 * Sends {@link NotificationMessage} via some kind of transport protocol.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface NotificationSenderService {

	void sendNotification(NotificationMessage message);

	String getDestinationAddress(Profile profile);
}
