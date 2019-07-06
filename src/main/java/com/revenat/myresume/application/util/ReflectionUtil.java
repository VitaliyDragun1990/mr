package com.revenat.myresume.application.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

public class ReflectionUtil {
	
	public static <T extends Annotation> int copyFields(final Object from, final Object to, Class<T> annotation) {
		final CopiedFieldsCounter copiedFieldsCounter = new CopiedFieldsCounter();
		ReflectionUtils.doWithFields(to.getClass(), new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalAccessException {
				ReflectionUtils.makeAccessible(field);
				copyAccessibleField(field, from, to, copiedFieldsCounter);
				
			}
		}, createFieldFilter(annotation));
		return copiedFieldsCounter.counter;
	}
	
	public static int copyFields(final Object from, final Object to) {
		return copyFields(from, to, null);
	}

	private static <T extends Annotation> FieldFilter createFieldFilter(Class<T> annotation) {
		if (annotation == null) {
			return ReflectionUtils.COPYABLE_FIELDS;
		} else {
			return new org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter(annotation);
		}
	}

	private static void copyAccessibleField(Field field, Object from, Object to, CopiedFieldsCounter copiedFieldsCounter)
			throws IllegalAccessException {
		Object fromValue = field.get(from);
		Object toValue = field.get(to);
		if (fromValue == null) {
			if (toValue != null) {
				field.set(to, null);
				copiedFieldsCounter.counter++;
			}
		} else {
			if (!fromValue.equals(toValue)) {
				field.set(to, toValue);
				copiedFieldsCounter.counter++;
			}
		}
	}

	public static Object readProperty(Object obj, String propertyName) {
		try {
			return BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName).getReadMethod().invoke(obj);
		} catch (IllegalAccessException | InvocationTargetException | RuntimeException e) {
			throw new IllegalArgumentException("Can't read property: '" + propertyName + "' from object: '"
					+ obj.getClass() + "': " + e.getMessage(), e);
		}
	}

	/**
	 * Checks whether all declared fields, provided {@code element} contains, have
	 * {@code null} values.
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isAllFieldsNull(Object element) {
		Field[] fields = element.getClass().getDeclaredFields();
		for (Field field : fields) {
			ReflectionUtils.makeAccessible(field);
			if (!Modifier.isStatic(field.getModifiers()) && ReflectionUtils.getField(field, element) != null) {
				return false;
			}
		}
		return true;
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

	private ReflectionUtil() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object instance, String fieldName) {
		Field field = ReflectionUtils.findField(instance.getClass(), fieldName);
		if (field == null) {
			throw new IllegalArgumentException("There is no field with name='" + fieldName + "' in class='"
					+ instance.getClass() + "'");
		}
		field.setAccessible(true);
		return (T) ReflectionUtils.getField(field, instance);
	}
	
	private static final class CopiedFieldsCounter {
		private int counter;
	}
}
