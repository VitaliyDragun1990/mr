package com.revenat.myresume.application.generator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revenat.myresume.application.exception.CantCompleteClientRequestException;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.util.DataUtil;

@Component
class DataGeneratorImpl implements DataGenerator {
	private final int generateUidSuffixLength;
	private final String generateUidAlphabet;
	private final int maxTryCountToGenerateUid;
	private final String generatedPasswordAlphabet;
	private final int generatedPasswordLength;
	
	@Autowired
	public DataGeneratorImpl(
			@Value("${generate.uid.suffix.length}") int generateUidSuffixLength,
			@Value("${generate.uid.alphabet}") String generateUidAlphabet,
			@Value("${generate.uid.max.try.count}") int maxTryCountToGenerateUid,
			@Value("${generate.password.alphabet}") String generatedPasswordAlphabet,
			@Value("${generate.password.length}") int generatedPasswordLength) {
		this.generateUidSuffixLength = generateUidSuffixLength;
		this.generateUidAlphabet = generateUidAlphabet;
		this.maxTryCountToGenerateUid = maxTryCountToGenerateUid;
		this.generatedPasswordAlphabet = generatedPasswordAlphabet;
		this.generatedPasswordLength = generatedPasswordLength;
	}

	@Override
	public String generateUid(String firstName, String lastName, ResultChecker uidChecker) {
		String baseUid = DataUtil.generateProfileUid(firstName, lastName);
		String uid = baseUid;
		for (int i = 0; uidChecker.isAcceptable(uid); i++) {
			uid = DataUtil.generateUidWithRandomSuffix(baseUid, generateUidAlphabet, generateUidSuffixLength);
			if (i >= maxTryCountToGenerateUid) {
				throw new CantCompleteClientRequestException("Can't generate unique uid for profile: "
						+ baseUid + ": maxTrycountToGenerateUid detected");
			}
		}
		return uid;
	}
	
	@Override
	public String generateRestoreAccessLink(String appHost, String token) {
		return appHost + "/restore/" + token;
	}
	
	@Override
	public String generateCertificateName(String fileName) {
		if (fileName == null) {
			return null;
		}
		int point = fileName.lastIndexOf('.');
		if (point != -1) {
			fileName = fileName.substring(0, point);
		}
		return DataUtil.capitalizeName(fileName);
	}
	
	@Override
	public String generateRandomPassword() {
		return DataUtil.generateRandomString(generatedPasswordAlphabet, generatedPasswordLength);
	}

}
