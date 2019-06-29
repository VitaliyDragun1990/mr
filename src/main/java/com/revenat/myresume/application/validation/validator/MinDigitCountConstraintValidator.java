package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.validation.annotation.MinDigitCount;

public class MinDigitCountConstraintValidator implements ConstraintValidator<MinDigitCount, CharSequence> {
	private int minDigitCount;
	
	@Override
	public void initialize(MinDigitCount constraintAnnotation) {
		minDigitCount = constraintAnnotation.value();
		
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		int digitCount = 0;
		for (int i = 0; i < value.length(); i++) {
			if (Character.isDigit(value.charAt(i))) {
				digitCount++;
			}
		}
		return digitCount >= minDigitCount;
	}

}
