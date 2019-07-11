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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Validation annotation to check whether some password value satisfies
 * specified number of rules and constraints.
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
@Documented
@Constraint(validatedBy = {})
@NotBlank
@Size(min = 8)
@EnglishLanguage
@MinDigitCount
@MinUpperCharCount
@MinLowerCharCount
@MinSpecSymbolsCount
public @interface PasswordStrength {

	String message() default "PasswordStrength";

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};
}
