package com.revenat.myresume.infrastructure.util;

import com.revenat.myresume.infrastructure.exception.InvalidParameterException;

/**
 * Utility class with checks
 * 
 * @author Vitaliy Dragun
 *
 */
public final class Checks {

	/**
	 * Verifies that specified check passed and throws exceptin otherwise.
	 * 
	 * @param check   some condition to verify
	 * @param message some message to pass to possible exception
	 * @throws InvalidParameterException if spcified {@code check} is failed.
	 */
	public static void checkParam(boolean check, String message) {
		if (!check) {
			throw new InvalidParameterException(message);
		}
	}

	private Checks() {
	}
}
