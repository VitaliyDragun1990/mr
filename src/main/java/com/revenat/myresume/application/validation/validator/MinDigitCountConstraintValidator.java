package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.validation.annotation.MinDigitCount;

public class MinDigitCountConstraintValidator implements ConstraintValidator<MinDigitCount, String> {
	private static final String NUMBERS = "0123456789";
	
	private int minDigitCount;
	
	@Override
	public void initialize(MinDigitCount constraintAnnotation) {
		minDigitCount = constraintAnnotation.value();
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		int digitCount = 0;
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (NUMBERS.indexOf(ch) != -1) {
				digitCount ++;
			}
		}
		return digitCount >= minDigitCount;
	}

}
