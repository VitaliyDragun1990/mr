package com.revenat.myresume.application.config.transaction;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

/**
 * May be placed on methods to emulate to some degree behaviour of
 * {@link Transactional} annotation in conjuction with
 * {@link TransactionalEmulationAspect}
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface EmulatedTransactional {

}
