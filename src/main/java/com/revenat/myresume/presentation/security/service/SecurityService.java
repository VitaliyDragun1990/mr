package com.revenat.myresume.presentation.security.service;

import javax.annotation.Nonnull;

import com.revenat.myresume.presentation.security.exception.InvalidRestoreAccessTokenException;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

/**
 * Manages security-related functionality.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface SecurityService {

	/**
	 * Restores access for user with specified {@code anyUniqueId} identifier
	 * 
	 * @param anyUniqueId the unique string identifier to look for a particular
	 *                    {@link AuthenticatedUser} object.
	 */
	void restoreAccess(@Nonnull String anyUniqueId);

	/**
	 * Resets password for a particular for a particular {@link AuthenticatedUser}
	 * 
	 * @param authUser    user for which password should be reseted
	 * @param token       restore token
	 * @param newPassword the new password to set
	 */
	void resetPassword(@Nonnull AuthenticatedUser authUser, @Nonnull String token, @Nonnull String newPassword);

	/**
	 * Updates password for a particular {@link AuthenticatedUser}.
	 * 
	 * @param authUser user for which password should be updated
	 * @param token    the password reset token string which should mach with one
	 *                 generated specific for the particular
	 *                 {@link AuthenticatedUser} instance
	 * @param password the new password to update
	 */
	void updatePassword(@Nonnull AuthenticatedUser authUser, @Nonnull String oldPassword, @Nonnull String newPassword);

	/**
	 * Processes specified restore access token
	 * 
	 * @param token the password reset token string
	 * @throws InvalidRestoreAccessTokenException if specified access token is
	 *                                            invalid
	 */
	void processPasswordResetToken(@Nonnull String token);

	/**
	 * Removes profile associated with profided {@link AuthenticatedUser} user. If
	 * specified user is authenticated, he will be automatically logged out after
	 * removal has been completed.
	 * 
	 * @param authUser
	 */
	void remove(@Nonnull AuthenticatedUser authUser);
}
