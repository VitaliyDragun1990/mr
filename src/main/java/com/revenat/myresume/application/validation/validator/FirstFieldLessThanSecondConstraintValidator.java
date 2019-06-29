package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.application.validation.annotation.FirstFieldLessThanSecond;

public class FirstFieldLessThanSecondConstraintValidator implements ConstraintValidator<FirstFieldLessThanSecond, Object> {
	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(FirstFieldLessThanSecond constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@SuppressWarnings("unchecked" )
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Object firstValue = ReflectionUtil.readProperty(value, firstFieldName);
			Object secondValue = ReflectionUtil.readProperty(value, secondFieldName);
			
			if (firstValue == null || secondValue == null) {
				return true;
			} else if (firstValue instanceof Comparable<?> && secondValue instanceof Comparable<?>) {
				return ((Comparable<Object>)firstValue).compareTo((Comparable<Object>)secondValue) <= 0;
			} else {
				throw new IllegalArgumentException("first and second fields are not comparable!!!");
			}
			
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
