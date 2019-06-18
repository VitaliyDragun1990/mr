package com.revenat.myresume.application.validation.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.validation.annotation.Phone;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {
	private static final String REGEXP = "^\\+(?:[0-9]\\s?){6,14}[0-9]$";

	@Override
	public void initialize(Phone constraintAnnotation) {
		// do nothing
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		Pattern p = Pattern.compile(REGEXP);
		return p.matcher(value).matches();
	}

}
