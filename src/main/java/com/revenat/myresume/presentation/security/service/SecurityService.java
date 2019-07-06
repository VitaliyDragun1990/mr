package com.revenat.myresume.presentation.security.service;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.revenat.myresume.presentation.security.exception.InvalidRestoreAccessTokenException;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

public interface SecurityService {

	/**
	 * Sets registration as completed for particular {@link AuthenticatedUser}
	 * 
	 * @param authenticatedUser
	 */
	void completeRegistration(@Nonnull AuthenticatedUser authenticatedUser);

	/**
	 * Retrieves a {@link AuthenticatedUser} instance using the given email.
	 * 
	 * @param email the unique string identifier to look for a particular
	 *              {@link AuthenticatedUser}
	 * @return the {@link Optional} with {@link AuthenticatedUser} instance with the
	 *         given email, or empty optional if nothing was found.
	 */
	@Nonnull Optional<AuthenticatedUser> findByEmail(@Nonnull String email);

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
