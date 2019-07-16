package com.revenat.myresume.application.transformer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.exception.TypeTransformingException;
import com.revenat.myresume.infrastructure.util.Checks;

@Service
class TransformerImpl implements Transformer {
	private final ConversionService conversionService;
	
	@Autowired
	public TransformerImpl(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Override
	public <T, E> E transform(T source, Class<E> destClass) {
		checkParams(source, destClass);
		
		try {
			return conversionService.convert(source, destClass);
		} catch (ConversionException e) {
			throw new TypeTransformingException("Error while transforming from object of class:" + source.getClass()
				+ " to object of class:" + destClass, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, E> List<E> transformToList(T source, Class<E> destClass) {
		checkParams(source, destClass);
		
		try {
			return (List<E>) conversionService.convert(
					source,
					TypeDescriptor.forObject(source),
					TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(destClass)));
		} catch (ConversionException e) {
			throw new TypeTransformingException("Error while transforming from object of class:" + source.getClass()
				+ " to list of objects of class:" + destClass, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, E> List<E> transformToList(List<T> source, Class<T> sourceClass, Class<E> destClass) {
		checkParams(source, sourceClass, destClass);
		
		try {
			return (List<E>) conversionService.convert(
					source,
					TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(sourceClass)),
					TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(destClass)));
		} catch (ConversionException e) {
			throw new TypeTransformingException("Error while transforming from list of object of class:" + sourceClass
			+ " to list of objects of class:" + destClass, e);
		}
	}

	private static <T, E> void checkParams(List<T> source, Class<T> sourceClass, Class<E> destClass) {
		Checks.checkParam(source != null, "source list with objects to transform from can not be null");
		Checks.checkParam(sourceClass != null, "sourceClass of object to transform from can not be null");
		Checks.checkParam(destClass != null, "destClass of object to transform to can not be null");
	}
	
	private static <T, E> void checkParams(T source, Class<E> destClass) {
		Checks.checkParam(source != null, "source object to transform from can not be null");
		Checks.checkParam(destClass != null, "destClass of object to transform to can not be null");
	}

}
