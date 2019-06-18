package com.revenat.myresume.application.validation.validator;

import java.time.Instant;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Calendar;
import java.util.Date;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Object firstObj = ReflectionUtil.getFieldValue(value, firstFieldName);
			Object secondObj = ReflectionUtil.getFieldValue(value, secondFieldName);
			
			if (firstObj == null && secondObj == null) {
				return true;
			}
			else if (firstObj == null) {
				return false;
			} else if (secondObj == null) {
				return true;
			}
			
			
			if (firstObj.getClass() != secondObj.getClass()) {
				return false;
			}
			
			if (firstObj instanceof ChronoLocalDate) {
				ChronoLocalDate first = (ChronoLocalDate) firstObj;
				ChronoLocalDate second = (ChronoLocalDate) secondObj;
				return first.isBefore(second);
			}
			
			if (firstObj instanceof ChronoLocalDateTime) {
				ChronoLocalDateTime first = (ChronoLocalDateTime) firstObj;
				ChronoLocalDateTime second = (ChronoLocalDateTime) secondObj;
				return first.isBefore(second);
			}
			
			if (firstObj instanceof Instant) {
				Instant first = (Instant) firstObj;
				Instant second = (Instant) secondObj;
				return first.isBefore(second);
			}
			
			if (firstObj instanceof Date) {
				Date first = (Date) firstObj;
				Date second = (Date) secondObj;
				return first.before(second);
			}
			
			if (firstObj instanceof Calendar) {
				Calendar first = (Calendar) firstObj;
				Calendar second = (Calendar) secondObj;
				return first.before(second);
			}
			if (firstObj instanceof Number) {
				Number first = (Number) firstObj;
				Number second = (Number) secondObj;
				return first.doubleValue() < second.doubleValue();
			}
			
		} catch (Exception ignore) {
			// ignore
		}
		return false;
	}

}
