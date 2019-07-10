package com.revenat.myresume.application.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Optional;

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
				copyAccessibleField(field.getName(), from, to, copiedFieldsCounter);
				
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

	private static void copyAccessibleField(String fieldName, Object from, Object to, CopiedFieldsCounter copiedFieldsCounter)
			throws IllegalAccessException {
		Optional<Field> fromFieldOptional = getField(from.getClass(), fieldName);
		// Skip unknown fields
		if (fromFieldOptional.isPresent()) {
			Field fromField = fromFieldOptional.get();
			ReflectionUtils.makeAccessible(fromField);
			Object fromValue = fromField.get(from);
			
			Optional<Field> toFieldOptional = getField(to.getClass(), fieldName);
			if (toFieldOptional.isPresent()) {
				Field toField = toFieldOptional.get();
				ReflectionUtils.makeAccessible(toField);
				Object toValue = toField.get(to);
				
				if (fromValue == null) {
					if (toValue != null) {
						toField.set(to, null);
						copiedFieldsCounter.counter++;
					}
				} else {
					if (!fromValue.equals(toValue)) {
						toField.set(to, fromValue);
						copiedFieldsCounter.counter++;
					}
				}
			}
		}
	}

	private static Optional<Field> getField(Class<?> clz, String fieldName) {
		Class<?> current = clz;
		while (current != null) {
			try {
				Field declaredField = current.getDeclaredField(fieldName);
				return Optional.of(declaredField);
			} catch (NoSuchFieldException | SecurityException e) {
				current = current.getSuperclass();
			}
		}
		return Optional.empty();
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
