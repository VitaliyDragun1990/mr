package com.revenat.myresume.application.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.revenat.myresume.application.validation.validator.FieldMatchConstraintValidator;

/**
 * https://github.com/Rudeg/Spring-MVC-Example/blob/master/Lab%202/src/main/java/com/springexample/common/constraits/FieldMatch.java
 * 
 * Validation annotation to validate that 2 fields have the same value. An array
 * of fields and their matching confirmation fields can be supplied.
 *
 * Example, compare 1 pair of fields:
 * 
 * @FieldMatch(first = "password", second = "confirmPassword", message ="The password fields must match")
 *
 * Example, compare more than 1 pair of fields: 
 * @FieldMatch.List({ @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
 * 					  @FieldMatch(first = "email", second = "confirmEmail", message ="The email fields must match")})
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Constraint(validatedBy = {FieldMatchConstraintValidator.class})
public @interface FieldMatch {
	
	String message() default "FieldMatch";
	
	String first();
	
	String second();
	
	Class<? extends Payload>[] payload() default {};
	
	Class<?>[] groups() default {};
	
	/**
	 * Defines several <code>@FieldMatch</code> annotations on the same element
	 *
	 * @see FieldMatch
	 */
	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		FieldMatch[] value();
	}

}
