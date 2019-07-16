package com.revenat.myresume.application.generator.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revenat.myresume.application.exception.DataGenerationException;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.util.DataUtil;
import com.revenat.myresume.infrastructure.util.Checks;

@Component
class DataGeneratorImpl implements DataGenerator {
	private final int generateUidSuffixLength;
	private final String generateUidAlphabet;
	private final int maxTryCountToGenerateUid;
	private final String generatedPasswordAlphabet;
	private final int generatedPasswordLength;
	private final String appHost;
	
	@Autowired
	public DataGeneratorImpl(
			@Value("${generate.uid.suffix.length}") int generateUidSuffixLength,
			@Value("${generate.uid.alphabet}") String generateUidAlphabet,
			@Value("${generate.uid.max.try.count}") int maxTryCountToGenerateUid,
			@Value("${generate.password.alphabet}") String generatedPasswordAlphabet,
			@Value("${generate.password.length}") int generatedPasswordLength,
			@Value("${app.host}") String appHost) {
		this.generateUidSuffixLength = generateUidSuffixLength;
		this.generateUidAlphabet = generateUidAlphabet;
		this.maxTryCountToGenerateUid = maxTryCountToGenerateUid;
		this.generatedPasswordAlphabet = generatedPasswordAlphabet;
		this.generatedPasswordLength = generatedPasswordLength;
		this.appHost = appHost;
	}

	@Override
	public String generateUid(String firstName, String lastName, ResultChecker uidChecker) {
		checkParams(firstName, lastName, uidChecker);
		
		String baseUid = DataUtil.generateProfileUid(firstName, lastName);
		String uid = baseUid;
		for (int i = 0; attemptFailed(uidChecker, uid); i++) {
			uid = DataUtil.generateUidWithRandomSuffix(baseUid, generateUidAlphabet, generateUidSuffixLength);
			if (isExceededTryCount(i)) {
				throw new DataGenerationException("Can't generate unique uid for profile: "
						+ baseUid + ": maxTrycountToGenerateUid detected");
			}
		}
		return uid;
	}
	
	@Override
	public String generateRestoreAccessLink(String token) {
		Checks.checkParam(token != null, "token to generate restore access link can not be null");
		
		return appHost + "/restore/" + token;
	}
	
	@Override
	public String generateCertificateName(String fileName) {
		if (fileName == null) {
			return null;
		}
		fileName = getRidOfFileExtension(fileName);
		return DataUtil.capitalizeName(fileName);
	}
	
	
	@Override
	public String generateRandomImageName() {
		return UUID.randomUUID().toString() + ".jpg";
	}

	@Override
	public String generateRandomPassword() {
		return DataUtil.generateRandomString(generatedPasswordAlphabet, generatedPasswordLength);
	}
	
	private String getRidOfFileExtension(String fileName) {
		int point = fileName.lastIndexOf('.');
		if (point != -1) {
			return fileName.substring(0, point);
		}
		return fileName;
	}
	
	private boolean attemptFailed(ResultChecker uidChecker, String uid) {
		return !uidChecker.isAcceptable(uid);
	}
	
	private boolean isExceededTryCount(int i) {
		return i >= maxTryCountToGenerateUid;
	}
	
	private static void checkParams(String firstName, String lastName, ResultChecker uidChecker) {
		Checks.checkParam(firstName != null, "firstName to generate uid for can not be null");
		Checks.checkParam(lastName != null, "lastName to generate uid for can not be null");
		Checks.checkParam(uidChecker != null, "uidChecker to verify generated uid with can not be null");
	}

}
