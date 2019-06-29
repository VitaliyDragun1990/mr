package com.revenat.myresume.application.util;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;

public class ReflectionUtil {
	
	public static Object readProperty(Object obj, String propertyName) {
		try {
			return BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName).getReadMethod().invoke(obj);
		} catch (IllegalAccessException | InvocationTargetException | RuntimeException e) {
			throw new IllegalArgumentException("Can't read property: '" + propertyName + "' from object: '" + obj.getClass()
					+ "': " + e.getMessage(), e);
		}
	}

//	public static Object getFieldValue(final Object obj, final String fieldName) {
//		Class<?> current = obj.getClass();
//		while (current != null) {
//			try {
//				Field f =  current.getDeclaredField(fieldName);
//				f.setAccessible(true);
//				return getValue(obj, f);
//			} catch (NoSuchFieldException | SecurityException e) {
//				current = current.getSuperclass();
//			}
//		}
//		throw new ApplicationException("No field " + fieldName + " in the class " + obj.getClass());
//	}
//
//	private static Object getValue(final Object obj, Field f) {
//		try {
//			return f.get(obj);
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			throw new ApplicationException(e);
//		}
//	}
	
	private ReflectionUtil() {}
}
