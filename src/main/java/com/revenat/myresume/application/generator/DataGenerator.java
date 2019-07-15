package com.revenat.myresume.application.generator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Convenient component for generating application-specific data
 * 
 * @author Vitaliy Dragun
 *
 */
public interface DataGenerator {

	@Nonnull
	String generateUid(@Nonnull String firstName, @Nonnull String lastName, @Nonnull ResultChecker resultChecker);

	@Nonnull
	String generateRestoreAccessLink(@Nonnull String token);

	@Nullable
	String generateCertificateName(@Nullable String fileName);
	
	@Nonnull
	String generateRandomImageName();

	@Nonnull
	String generateRandomPassword();

	@FunctionalInterface
	interface ResultChecker {
		boolean isAcceptable(String result);
	}
}
