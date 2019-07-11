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

import com.revenat.myresume.application.validation.validator.AdulthoodConstraintValidator;

/**
 * Validation annotation to check whether specified value represents valid
 * adulthood value
 * 
 * @author Vitaliy Dragun
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
@Constraint(validatedBy = { AdulthoodConstraintValidator.class })
public @interface Adulthood {

	String message() default "Adulthood";

	int adulthoodAge() default 18;

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};
}
