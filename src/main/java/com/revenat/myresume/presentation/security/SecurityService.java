package com.revenat.myresume.presentation.security;

import java.util.Optional;

public interface SecurityService {
	
	/**
	 * Sets registration as completed for particular {@link AuthenticatedUser}
	 * @param authenticatedUser
	 */
	void completeRegistration(AuthenticatedUser authenticatedUser);

	/**
	 * Retrieves a {@link AuthenticatedUser} instance using the given email.
	 * 
	 * @param email the unique string identifier to look for a particular
	 *              {@link AuthenticatedUser}
	 * @return the {@link Optional} with {@link AuthenticatedUser} instance with the
	 *         given email, or empty optional if nothing was found.
	 */
	Optional<AuthenticatedUser> findByEmail(String email);

	/**
	 * Sets special password reset token for a particular {@link AuthenticatedUser}
	 * found with the given email
	 * 
	 * @param email the unique string identifier to look for a particular
	 *              {@link AuthenticatedUser} object.
	 * @return the password reset token string which was set for
	 *         {@link AuthenticatedUser} with given email.
	 */
	String resetPassword(String email);

	/**
	 * Updates the password for a particular {@link AuthenticatedUser} instance
	 * 
	 * @param email    the unique string identifier to look for a particular
	 *                 {@link AuthenticatedUser} instance.
	 * @param token    the password reset token string which should mach with one
	 *                 generated specific for the particular
	 *                 {@link AuthenticatedUser} instance
	 * @param password the new password to update
	 */
	void updatePassword(String email, String token, String password);

	/**
	 * Verifies that the given token is the same as the one generated for
	 * {@link AuthenticatedUser} with the given email.
	 * 
	 * @param email the unique string identifier to look for a particular
	 *              {@link AuthenticatedUser} instance.
	 * @param token the password reset token string
	 * @return {@code true} if the specified {@code toke} is equal to one generated
	 *         specifically for the {@link AuthenticatedUser} with the given email,
	 *         {@code false} otherwise.
	 */
	boolean verifyPasswordResetToken(String email, String token);
}
