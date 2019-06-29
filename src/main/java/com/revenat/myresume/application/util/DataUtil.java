package com.revenat.myresume.application.util;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.text.WordUtils;

public class DataUtil {
	private static final String UID_DELIMITER = "-";

	public static String normalizeName(String name) {
		return name.trim().toLowerCase();
	}
	
	public static String capitalizeName(String name) {
		return WordUtils.capitalize(name);
	}
	
	public static String generateProfileUid(String firstName, String lastName) {
		return normalizeName(firstName) + UID_DELIMITER + normalizeName(lastName);
	}
	
	public static String generateUidWithRandomSuffix(String baseUid, String alphabet, int letterCount) {
		return baseUid + UID_DELIMITER + generateRandomString(alphabet, letterCount);
	}
	
	public static String generateRandomString(String alphabet, int letterCount) {
		ThreadLocalRandom r = ThreadLocalRandom.current();
		StringBuilder uid = new StringBuilder();
		for (int i = 0; i < letterCount; i++) {
			uid.append(alphabet.charAt(r.nextInt(alphabet.length())));
		}
		return uid.toString();
	}

	private DataUtil() {}
}
