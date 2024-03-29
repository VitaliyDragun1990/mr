package com.revenat.myresume.application.service.notification;

import javax.annotation.Nonnull;

import com.revenat.myresume.domain.entity.Profile;

/**
 * Business service responsible for sending notifications
 * 
 * @author Vitaliy Dragun
 *
 */
public interface NotificationManagerService {

	/**
	 * Sends notification with link to owner of the {@code profile}, who should
	 * follow it to reset forgotten password
	 * 
	 * @param profile     {@link Profile} which owner should get notification
	 * @param restoreLink link owner of the profile should follow to set new
	 *                    password
	 */
	void sendRestoreAccessLink(@Nonnull Profile profile, @Nonnull String restoreLink);

	/**
	 * Sends notification to the profile owner with info that the password has been
	 * changed
	 * 
	 * @param profile {@link Profile} which owner should get notification
	 */
	void sendPasswordChanged(@Nonnull Profile profile);
	
	/**
	 * Sends notification to the profile owner with info that the password has been
	 * generated
	 * 
	 * @param profile {@link Profile} which owner should get notification
	 * @param generatedPassword generated password
	 */
	void sendPasswordGenerated(@Nonnull Profile profile, @Nonnull String generatedPassword);
}
