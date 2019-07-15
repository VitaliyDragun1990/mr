package com.revenat.myresume.application.config.transaction;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.transaction.support.TransactionSynchronization;

/**
 * May be placed on methods to enable support for registering
 * {@link TransactionSynchronization} actions. Used in conjuction with
 * {@link TransactionSynchronizationSupport} component
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface EnableTransactionSynchronization {

}
