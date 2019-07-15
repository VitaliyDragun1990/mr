package com.revenat.myresume.application.config.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * This component allows to register {@link TransactionSynchronization} actions
 * for environment where transaction support can't be easily enabled (e.g. when
 * JPA/appropriate application server is not used) and
 * PlatformTransactionManager can't be used. It sole purpose is to allow to
 * register transaction synchronization actions for events like after commit or
 * when transactin is failed and will be rolled back. If inside method annotated
 * with {@link EnableTransactionSynchronization} annotation transaction
 * synchronization is registered for
 * {@link TransactionSynchronization#STATUS_COMMITTED} and method completed
 * without throwing unchecked or by throwing the checked one, then such
 * synchronization will be called after method has completed. Also if inside
 * method annotated with {@link Transactional} annotation transaction
 * synchronization is registered for
 * {@link TransactionSynchronization#STATUS_ROLLED_BACK} and such method
 * completed by throwing unchecked exception, then such synchronization will be
 * called.
 * 
 * @author Vitaliy Dragun
 *
 */
@Aspect
public class TransactionSynchronizationSupport {
	private static final ThreadLocal<Integer> numberOfNestedTransactions = new ThreadLocal<>();

	@Around("@annotation(com.revenat.myresume.application.config.transaction.EnableTransactionSynchronization)")
	public Object advice(ProceedingJoinPoint pjp) throws Throwable {
		initializeSynchronization();
		try {
			return executeTransactionalMethod(pjp);
		} catch (Exception e) {
			processException(e);
			throw e;
		} finally {
			clearSynchronization();
		}
	}

	private Object executeTransactionalMethod(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		for (TransactionSynchronization transactionSynchronization : TransactionSynchronizationManager
				.getSynchronizations()) {
			transactionSynchronization.afterCommit();
		}
		return result;
	}

	private void processException(Exception e) {
		for (TransactionSynchronization transactionSynchronization : TransactionSynchronizationManager
				.getSynchronizations()) {
			if (e instanceof RuntimeException) {
				transactionSynchronization.afterCompletion(TransactionSynchronization.STATUS_ROLLED_BACK);
			} else {
				transactionSynchronization.afterCommit();
			}
		}
	}

	private void initializeSynchronization() {
		if (isNewTransaction()) {
			TransactionSynchronizationManager.initSynchronization();
		} else {
			incrementNestedTransactionCount();
		}
	}

	private void clearSynchronization() {
		if (isNestedTransaction()) {
			decrementNestendTransactionCount();
		} else {
			TransactionSynchronizationManager.clearSynchronization();
		}
	}

	private boolean isNestedTransaction() {
		Integer nestedTransactionsCount = numberOfNestedTransactions.get();
		return nestedTransactionsCount != null && nestedTransactionsCount > 0;
	}

	private void decrementNestendTransactionCount() {
		Integer nestedTransactionCount = numberOfNestedTransactions.get();
		if (nestedTransactionCount == null) {
			numberOfNestedTransactions.set(0);
		} else {
			numberOfNestedTransactions.set(nestedTransactionCount - 1);
		}
	}

	private void incrementNestedTransactionCount() {
		Integer nestedTransactionCount = numberOfNestedTransactions.get();
		if (nestedTransactionCount == null) {
			numberOfNestedTransactions.set(1);
		} else {
			numberOfNestedTransactions.set(nestedTransactionCount + 1);
		}
	}

	private boolean isNewTransaction() {
		return !TransactionSynchronizationManager.isSynchronizationActive();
	}
}
