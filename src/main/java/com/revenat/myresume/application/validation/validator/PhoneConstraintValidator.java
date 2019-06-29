package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.revenat.myresume.application.validation.annotation.Phone;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {

	@Override
	public void initialize(Phone constraintAnnotation) {
		// do nothing
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		try {
			Phonenumber.PhoneNumber number = PhoneNumberUtil.getInstance().parse(value, "");
			return PhoneNumberUtil.getInstance().isValidNumber(number);
		} catch (NumberParseException e) {
			return false;
		}
	}

}
