package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.validation.annotation.MinLowerCharCount;

public class MinLowerCharCountConstraintValidator implements ConstraintValidator<MinLowerCharCount, CharSequence> {
	private int minLowerCharCount;
	
	@Override
	public void initialize(MinLowerCharCount constraintAnnotation) {
		minLowerCharCount = constraintAnnotation.value();
		
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		int lowerCharCount = 0;
		for (int i = 0; i < value.length(); i++) {
			if (Character.isLowerCase(value.charAt(i))) {
				lowerCharCount++;
			}
		}
		return lowerCharCount >= minLowerCharCount;
	}

}
