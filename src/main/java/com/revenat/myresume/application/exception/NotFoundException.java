package com.revenat.myresume.application.exception;

import java.io.Serializable;

import com.revenat.myresume.domain.exception.ApplicationException;

/**
 * Represents specific kind of exception when needed resource
 * can't be found.
 */
public class NotFoundException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	
	private final Class<?> resourceClass;
	private final String resourcePropertyName;
	private final Serializable resourcePropertyValue;

	public <T, V extends Serializable> NotFoundException(Class<T> clazz, String propertyName, V propertyValue) {
		super(String.format("%s with %s: %s does not exists!", clazz.getSimpleName(), propertyName, propertyValue));
		this.resourceClass = clazz;
		this.resourcePropertyName = propertyName;
		this.resourcePropertyValue = propertyValue;
	}

	public Class<?> getResourceClass() {
		return resourceClass;
	}

	public String getResourcePropertyName() {
		return resourcePropertyName;
	}

	public Object getResourcePropertyValue() {
		return resourcePropertyValue;
	}
}
