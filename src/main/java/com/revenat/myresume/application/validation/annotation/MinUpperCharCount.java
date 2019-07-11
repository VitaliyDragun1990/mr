package com.revenat.myresume.application.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.revenat.myresume.application.validation.validator.MinUpperCharCountConstraintValidator;

/**
 * Validation annotation to check whether some value contains minimum number of
 * upper case chracters in it.
 * 
 * @author Vitaliy Dragun
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
@Constraint(validatedBy = {MinUpperCharCountConstraintValidator.class})
public @interface MinUpperCharCount {

	int value() default 1;
	
	String message() default "MinUpperCharCount";
	
	Class<? extends Payload>[] payload() default {};
	
	Class<?>[] groups() default {};
}
