package com.revenat.myresume.domain.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.revenat.myresume.domain.document.Profile;

/**
 * Designates {@link Profile} properties (fields or getters) that represent optional info.
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface OptionalData {

}
