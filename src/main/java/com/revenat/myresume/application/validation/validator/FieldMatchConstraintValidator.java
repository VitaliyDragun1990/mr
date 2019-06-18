package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import com.revenat.myresume.application.validation.annotation.FieldMatch;

public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Object firstObj = BeanUtils.getProperty(value, firstFieldName);
			Object secondObj = BeanUtils.getProperty(value, secondFieldName);
			
			return (firstObj == null && secondObj == null) || firstObj != null && firstObj.equals(secondObj);
		} catch (Exception ignore) {
			// ignore
		}
		return true;
	}

}
