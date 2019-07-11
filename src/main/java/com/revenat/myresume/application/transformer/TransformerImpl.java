package com.revenat.myresume.application.transformer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

@Service
class TransformerImpl implements Transformer {
	private final ConversionService conversionService;
	
	@Autowired
	public TransformerImpl(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Override
	public <T, E> E transform(T source, Class<E> destClass) {
		return conversionService.convert(source, destClass);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, E> List<E> transformToList(T source, Class<E> destClass) {
		return (List<E>) conversionService.convert(
				source,
				TypeDescriptor.forObject(source),
				TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(destClass)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, E> List<E> transformToList(List<T> source, Class<T> sourceClass, Class<E> destClass) {
		return (List<E>) conversionService.convert(
				source,
				TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(sourceClass)),
				TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(destClass)));
	}

}
