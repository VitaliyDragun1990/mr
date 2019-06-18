package com.revenat.myresume.application.generator;

public interface UidGenerator {

	String generateUid(String firstName, String lastName, UidChecker uidChecker);
	
	@FunctionalInterface
	interface UidChecker {
		boolean isOccupied(String uid);
	}
}
