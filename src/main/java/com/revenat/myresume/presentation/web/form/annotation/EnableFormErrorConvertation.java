package com.revenat.myresume.presentation.web.form.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Takes part into converting ordinal class(field) related exceptions into
 * form's field errors.
 * 
 * @author Vitaliy Dragun
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface EnableFormErrorConvertation {

	String formName();

	String fieldReference();

	Class<? extends Annotation>[] validationAnnotationClasses();

	@Documented
	@Retention(RUNTIME)
	@Target(TYPE)
	@interface List {
		EnableFormErrorConvertation[] value();
	}
}
