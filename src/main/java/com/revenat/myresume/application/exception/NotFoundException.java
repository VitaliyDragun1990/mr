package com.revenat.myresume.application.exception;

import java.io.Serializable;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Represents specific kind of exception when needed resource
 * can't be found.
 */
public class NotFoundException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	
	private final Class<?> clazz;
	private final String propertyName;
	private final Serializable propertyValue;

	public <T, V extends Serializable> NotFoundException(Class<T> clazz, String propertyName, V propertyValue) {
		super(String.format("%s with %s: %s does not exists!", clazz.getSimpleName(), propertyName, propertyValue));
		this.clazz = clazz;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}
}
