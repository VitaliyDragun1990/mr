package com.revenat.myresume.application.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.Instant;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.revenat.myresume.application.validation.validator.FirstFieldLessThanSecondConstraintValidator;

/**
 * Currently supports field comparison of type {@link Number} and derivatives,
 * {@link Date} and derivatives, {@link Instant}, {@link ChronoLocalDate} and
 * derivatives, {@link ChronoLocalDateTime} and derivatives. Example, compare 1
 * pair of fields:
 * 
 * @FieldMatch(first = "startDate", second = "endDate", message = "startDate
 *                   should be before endtDate")
 *
 *                   Example, compare more than 1 pair of
 *                   fields: @FirstFieldLessThanSecond.List({ @FirstFieldLessThanSecond(first
 *                   = "startDate", second = "endDate", message = "startDate
 *                   should be before endtDate"),
 * @FirstFieldLessThanSecond(first = "birthDay", second = "graduationDate")})
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Constraint(validatedBy = {FirstFieldLessThanSecondConstraintValidator.class})
public @interface FirstFieldLessThanSecond {

	String message() default "FirstFieldLessThanSecond";
	
	String first();
	
	String second();

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};

	/**
	 * Defines several <code>@FirstFieldLessThanSecond</code> annotations on the
	 * same element
	 *
	 * @see FirstFieldLessThanSecond
	 */
	@Target({ TYPE, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		FirstFieldLessThanSecond[] value();
	}

}
