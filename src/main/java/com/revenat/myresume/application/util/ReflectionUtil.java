package com.revenat.myresume.application.util;

import java.lang.reflect.Field;

import com.revenat.myresume.application.exception.ApplicationException;

public class ReflectionUtil {

	public static Object getFieldValue(final Object obj, final String fieldName) {
		Class<?> current = obj.getClass();
		while (current != null) {
			try {
				Field f =  current.getDeclaredField(fieldName);
				f.setAccessible(true);
				return getValue(obj, f);
			} catch (NoSuchFieldException | SecurityException e) {
				current = current.getSuperclass();
			}
		}
		throw new ApplicationException("No field " + fieldName + " in the class " + obj.getClass());
	}

	private static Object getValue(final Object obj, Field f) {
		try {
			return f.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ApplicationException(e);
		}
	}
	
	private ReflectionUtil() {}
}
