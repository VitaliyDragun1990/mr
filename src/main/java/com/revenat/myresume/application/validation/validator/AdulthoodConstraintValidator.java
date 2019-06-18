package com.revenat.myresume.application.validation.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.validation.annotation.Adulthood;

public class AdulthoodConstraintValidator implements ConstraintValidator<Adulthood, LocalDate> {
	private int adulthoodAge;

	@Override
	public void initialize(Adulthood constraintAnnotation) {
		adulthoodAge = constraintAnnotation.adulthoodAge();
		
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		int age = (int) ChronoUnit.YEARS.between(value, LocalDate.now());
		return age >= adulthoodAge;
	}

}
