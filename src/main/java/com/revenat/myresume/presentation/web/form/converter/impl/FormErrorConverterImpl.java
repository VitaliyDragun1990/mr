package com.revenat.myresume.presentation.web.form.converter.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.revenat.myresume.application.util.ReflectionUtil;
import com.revenat.myresume.presentation.web.form.annotation.EnableFormErrorConvertation;
import com.revenat.myresume.presentation.web.form.converter.FormErrorConverter;

@Component
class FormErrorConverterImpl implements FormErrorConverter {

	@Override
	public void convertFromErrorToFieldError(Object formInstance, Object validatedInstance, BindingResult bindingResult) {
		List<EnableFormErrorConvertation> metaAnnotations = findMetaAnnotations(formInstance);
		boolean found = false;
		for (EnableFormErrorConvertation metaAnnotation : metaAnnotations) {
			List<Annotation> validationAnnotations =
					findValidationAnnotaion(metaAnnotation.validationAnnotationClasses(), validatedInstance);
			if (!validationAnnotations.isEmpty()) {
				for (Annotation validationAnnotation : validationAnnotations) {
					processGlobalErrorConvertation(validationAnnotation, metaAnnotation, validatedInstance, bindingResult);
					processFormFieldErrorConvertation(validationAnnotation, metaAnnotation, bindingResult);
				}
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("validationAnnotationClass not found for EnableFormErrorConvertation annotation: "
					+ "validationAnnotationClass(es)=" + getValidatedAnnotationClasses(metaAnnotations)
					+ " validatedInstance=" + validatedInstance);
		}
		
	}

	private void processFormFieldErrorConvertation(Annotation validationAnnotation,
			EnableFormErrorConvertation metaAnnotation, BindingResult bindingResult) {
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			if (fieldError.getField().startsWith("items[") && fieldError.getField().endsWith("]")) {
				for (String code : fieldError.getCodes()) {
					if (getCodeForAnnotation(validationAnnotation).equals(code)) {
						createFieldErrorForErrorCode(metaAnnotation, fieldError, bindingResult);
					}
				}
			}
		}
	}

	private void processGlobalErrorConvertation(Annotation validationAnnotation,
			EnableFormErrorConvertation metaAnnotation, Object validatedInstance, BindingResult bindingResult) {
		for (ObjectError objectError : bindingResult.getGlobalErrors()) {
			for (String code : objectError.getCodes()) {
				if (getCodeForAnnotation(validationAnnotation).equals(code)) {
					createFieldErrorForErrorCode(metaAnnotation, objectError, validatedInstance, bindingResult);
					return;
				}
			}
		}
	}

	private void createFieldErrorForErrorCode(EnableFormErrorConvertation metaAnnotation, ObjectError objectError,
			Object validatedInstance, BindingResult bindingResult) {
		String fieldName = metaAnnotation.fieldReference();
		String formName = metaAnnotation.formName();
		Object value = validatedInstance instanceof Iterable<?> ? null : ReflectionUtil.readProperty(validatedInstance, fieldName);
		bindingResult.addError(
				new FieldError(formName, fieldName, value, false, objectError.getCodes(), objectError.getArguments(),
						objectError.getDefaultMessage()));
	}
	
	private void createFieldErrorForErrorCode(EnableFormErrorConvertation metaAnnotation, FieldError fieldError,
			BindingResult bindingResult) {
		String fieldName = metaAnnotation.fieldReference();
		String formName = metaAnnotation.formName();
		Object value = ReflectionUtil.readProperty(fieldError.getRejectedValue(), fieldName);
		bindingResult.addError(
				new FieldError(formName, fieldError.getField() + "." + fieldName, value, false, fieldError.getCodes(),
						fieldError.getArguments(), fieldError.getDefaultMessage()));
	}

	private String getCodeForAnnotation(Annotation validationAnnotation) {
		return validationAnnotation.annotationType().getSimpleName();
	}
	
	private String getValidatedAnnotationClasses(List<EnableFormErrorConvertation> metaAnnotations) {
		return metaAnnotations.stream()
				.flatMap(a -> Arrays.stream(a.validationAnnotationClasses()))
				.map(Class::getSimpleName)
				.collect(Collectors.joining(","));
	}

	private List<Annotation> findValidationAnnotaion(Class<? extends Annotation>[] validationAnnotationClasses,
			Object validatedInstance) {
		List<Annotation> list = new ArrayList<>();
		for (Class<? extends Annotation> validationAnnotationClass : validationAnnotationClasses) {
			Annotation annotation = findAnnotation(validationAnnotationClass, validatedInstance);
			if (annotation != null) {
				list.add(annotation);
			}
		}
		return list;
	}

	private List<EnableFormErrorConvertation> findMetaAnnotations(Object formInstance) {
		EnableFormErrorConvertation metaAnnotation = findAnnotation(EnableFormErrorConvertation.class, formInstance);
		if (metaAnnotation != null) {
			return Collections.singletonList(metaAnnotation);
		}
		EnableFormErrorConvertation.List list = findAnnotation(EnableFormErrorConvertation.List.class, formInstance);
		if (list != null) {
			return Arrays.asList(list.value());
		}
		throw new IllegalArgumentException("metaAnnotation not found for formInstance: " + formInstance.getClass());
	}

	private <T extends Annotation> T findAnnotation(Class<T> annotationClass, Object instance) {
		if (instance instanceof Iterable<?>) {
			instance = ((Iterable<?>)instance).iterator().next();
		}
		return AnnotationUtils.findAnnotation(instance.getClass(), annotationClass);
	}
}
