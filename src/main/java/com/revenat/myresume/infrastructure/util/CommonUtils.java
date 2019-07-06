package com.revenat.myresume.infrastructure.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;
import com.revenat.myresume.application.util.ReflectionUtil;

/**
 * Contains utility functions of the general purpose
 * 
 * @author Vitaly Dragun
 *
 */
public class CommonUtils {

	private CommonUtils() {
	}

	/**
	 * Returns not-null unmodifiable copy of the source set
	 */
	public static <T> Set<T> getSafeSet(Set<T> source) {
		return Collections.unmodifiableSet(Optional.ofNullable(source).orElse(Collections.emptySet()));
	}

	/**
	 * Returns non-null unmodifiable copy of the source list
	 */
	public static <T> List<T> getSafeList(List<T> source) {
		return Collections.unmodifiableList(Optional.ofNullable(source).orElse(Collections.emptyList()));
	}

	/**
	 * Returns non-null unmodifiable copy of the source map
	 */
	public static <K, V> Map<K, V> getSafeMap(Map<K, V> source) {
		return Collections.unmodifiableMap(Optional.ofNullable(source).orElse(Collections.emptyMap()));
	}

	/**
	 * Generates string representation of the specified object
	 */
	public static String toString(Object param) {
		return ReflectionToStringBuilder.toString(param, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static boolean isBlank(String string) {
		return StringUtils.isBlank(string);
	}

	public static boolean isNotBlank(String string) {
		return !StringUtils.isBlank(string);
	}

	/**
	 * Checks whether specified {@link List} instances are equal - have equal
	 * elements in equal order.
	 */
	public static boolean isEqualList(final List<?> a, final List<?> b) {
		if (a.size() != b.size()) {
			return false;
		}
		for (int i = 0; i < a.size(); i++) {
			if (!Objects.equal(a.get(i), b.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Removes all {@code null}s and elements where all thair fields have null value
	 * from specified collection
	 * 
	 * @param collection collection to remove empty elements from
	 */
	public static void removeEmptyElements(Collection<?> collection) {
		Iterator<?> it = collection.iterator();
		while (it.hasNext()) {
			Object element = it.next();
			if (element == null || isAllFieldsNull(element)) {
				it.remove();
			}
		}
	}

	/**
	 * Compares two comparable values
	 * 
	 * @param isNullFirst whether {@code null} value should be threated as greater
	 *                    one
	 * @return {@code -1} if {@code firstValue} is less that {@code secondValue},
	 *         {@code 0} if they are equals, {@code 1} if {@code firstValue} is
	 *         greater that {@code secondValue}
	 */
	@SuppressWarnings("unchecked")
	public static <T> int compareValues(Comparable<T> firstValue, Comparable<T> secondValue, boolean isNullFirst) {
		if (firstValue == null) {
			if (secondValue == null) {
				return 0;
			} else {
				return isNullFirst ? 1 : -1;
			}
		} else {
			if (secondValue == null) {
				return isNullFirst ? -1 : 1;
			} else {
				return firstValue.compareTo((T) secondValue);
			}
		}
	}

	private static boolean isAllFieldsNull(Object element) {
		return ReflectionUtil.isAllFieldsNull(element);
	}

	public static boolean isNullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
}