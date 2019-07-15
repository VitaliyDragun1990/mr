package com.revenat.myresume.application.transformer;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Component responsible for transforming object of one type to object of another type.
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
	@Nonnull
	<T, E> E transform(@Nonnull T source, @Nonnull Class<E> destClass);

	/**
	 * Transforms object {@code source} into {@link List} of objects of class {@code destClass}
	 * @param <T> type of the source object
	 * @param <E> type of the destination object
	 * @param source object to transform from
	 * @param destClass class of the object to transform to
	 * @return list of objects of class {@code destClass}
	 */
	@Nonnull
	<T, E> List<E> transformToList(@Nonnull T source, @Nonnull Class<E> destClass);

	/**
	 * Transforms {@link List} of objects {@code source} into {@link List} of objects of class {@code destClass}
	 * @param <T> type of the source object
	 * @param <E> type of the destination object
	 * @param source list of objects to transform from
	 * @param sourceClass class of the object to transform from
	 * @param destClass class of the object to transform to
	 * @return list of objects of class {@code destClass}
	 */
	@Nonnull
	<T, E> List<E> transformToList(@Nonnull List<T> source, @Nonnull Class<T> sourceClass, @Nonnull Class<E> destClass);
}
