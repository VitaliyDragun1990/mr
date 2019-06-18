package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.validation.annotation.MinLowerCharCount;

public class MinLowerCharCountConstraintValidator implements ConstraintValidator<MinLowerCharCount, String> {
	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
	
	private int minLowerCharCount;
	
	@Override
	public void initialize(MinLowerCharCount constraintAnnotation) {
		minLowerCharCount = constraintAnnotation.value();
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		int lowerCharCount = 0;
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (LETTERS.indexOf(ch) != -1) {
				lowerCharCount++;
			}
		}
		return lowerCharCount >= minLowerCharCount;
	}

}
