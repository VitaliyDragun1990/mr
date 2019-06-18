package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.validation.annotation.MinUpperCharCount;

public class MinUpperCharCountConstraintValidator implements ConstraintValidator<MinUpperCharCount, String> {
	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private int minUpperCharCount;
	
	@Override
	public void initialize(MinUpperCharCount constraintAnnotation) {
		minUpperCharCount = constraintAnnotation.value();
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		int upperCharCount = 0;
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (LETTERS.indexOf(ch) != -1) {
				upperCharCount++;
			}
		}
		return upperCharCount >= minUpperCharCount;
	}

}
