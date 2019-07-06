package com.revenat.myresume.application.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.FieldError;

import com.revenat.myresume.domain.exception.ApplicationException;

public class DTOValidationException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	private static final String[] EMPTY_ARRAY = new String[] {};

	private final String fieldName;
	private final transient Object rejectedValue;
	private final String errorCode;

	public DTOValidationException(String fieldName, Object rejectedValue, String errorCode) {
		super("");
		this.fieldName = fieldName;
		this.rejectedValue = rejectedValue;
		this.errorCode = errorCode;
	}

	public DTOValidationException(String fieldName, Object rejectedValue) {
		this(fieldName, rejectedValue, null);
	}
	
	public DTOValidationException(String fieldName) {
		this(fieldName, null);
	}
	
	public FieldError buildFieldError(String objectName) {
		List<String> codes = new ArrayList<>();
		if (errorCode != null) {
			codes.add(errorCode);
		}
		codes.addAll(Arrays.asList(
				DTOValidationException.class.getSimpleName() + "." + objectName + "." + fieldName,
				objectName + "." + fieldName,
				fieldName
				));
		return new FieldError(objectName, fieldName, rejectedValue, false, codes.toArray(EMPTY_ARRAY), EMPTY_ARRAY, toString());
	}
	
	@Override
	public String toString() {
		return String.format("DTOValidationException [fieldName=%s, rejectedValue=%s, errorCode=%s]", fieldName, rejectedValue, errorCode);
	}

}
