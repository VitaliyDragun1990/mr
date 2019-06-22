package com.revenat.myresume.application.generator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revenat.myresume.application.exception.CantCompleteClientRequestException;
import com.revenat.myresume.application.generator.UidGenerator;
import com.revenat.myresume.application.util.DataUtil;

@Component
class UidGeneratorImpl implements UidGenerator {
	private final int generateUidSuffixLength;
	private final String generateUidAlphabet;
	private final int maxTryCountToGenerateUid;
	
	@Autowired
	public UidGeneratorImpl(
			@Value("${generate.uid.suffix.length}") int generateUidSuffixLength,
			@Value("${generate.uid.alphabet}") String generateUidAlphabet,
			@Value("${generate.uid.max.try.count}") int maxTryCountToGenerateUid) {
		this.generateUidSuffixLength = generateUidSuffixLength;
		this.generateUidAlphabet = generateUidAlphabet;
		this.maxTryCountToGenerateUid = maxTryCountToGenerateUid;
	}



	@Override
	public String generateUid(String firstName, String lastName, UidChecker uidChecker) {
		String baseUid = DataUtil.generateProfileUid(firstName, lastName);
		String uid = baseUid;
		for (int i = 0; uidChecker.isOccupied(uid); i++) {
			uid = DataUtil.generateUidWithRandomSuffix(baseUid, generateUidAlphabet, generateUidSuffixLength);
			if (i >= maxTryCountToGenerateUid) {
				throw new CantCompleteClientRequestException("Can't generate unique uid for profile: "
						+ baseUid + ": maxTrycountToGenerateUid detected");
			}
		}
		return uid;
	}

}
