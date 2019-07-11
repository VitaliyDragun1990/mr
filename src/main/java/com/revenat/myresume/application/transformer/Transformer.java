package com.revenat.myresume.application.transformer;

import java.util.List;

/**
 * Component responsible for transforming one kind of object into another one.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface Transformer {

	/**
	 * Transforms object {@code source} into object of class {@code destClass}
	 * @param <T> type of the source object
	 * @param <E> type of the destination object
	 * @param source object to transform from
	 * @param destClass class of the object to transform to
	 * @return object of class {@code destClass}
	 */
	<T, E> E transform(T source, Class<E> destClass);

	/**
	 * Transforms object {@code source} into {@link List} of objects of class {@code destClass}
	 * @param <T> type of the source object
	 * @param <E> type of the destination object
	 * @param source object to transform from
	 * @param destClass class of the object to transform to
	 * @return list of objects of class {@code destClass}
	 */
	<T, E> List<E> transformToList(T source, Class<E> destClass);

	/**
	 * Transforms {@link List} of objects {@code source} into {@link List} of objects of class {@code destClass}
	 * @param <T> type of the source object
	 * @param <E> type of the destination object
	 * @param source list of objects to transform from
	 * @param sourceClass class of the object to transform from
	 * @param destClass class of the object to transform to
	 * @return list of objects of class {@code destClass}
	 */
	<T, E> List<E> transformToList(List<T> source, Class<T> sourceClass, Class<E> destClass);
}
