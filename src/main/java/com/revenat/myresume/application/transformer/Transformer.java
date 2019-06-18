package com.revenat.myresume.application.transformer;

import java.util.List;

public interface Transformer {

	<T, E> E transform(T source, Class<E> destClass);
	
	<T, E> List<E> transformToList(T source, Class<E> destClass);
	
	<T, E> List<E> transformToList(List<T> source, Class<T> sourceClass, Class<E> destClass);
}
