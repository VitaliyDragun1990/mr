package com.revenat.myresume.infrastructure.util;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public final class TransactionUtils {

	/**
	 * Allows to register {@link TransactionSynchronization} on transaction is
	 * succeeded event
	 * 
	 * @param action
	 */
	public static void executeIfTransactionSucceeded(Runnable action) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			private boolean isCompleted = false;

			@Override
			public void afterCommit() {
				if (!isCompleted) {
					action.run();
					isCompleted = true;
				}
			}
		});
	}

	/**
	 * Allows to register {@link TransactionSynchronization} on transaction is
	 * failed event
	 * 
	 * @param action
	 */
	public static void executeIfTransactionFailed(Runnable action) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			private boolean isCompleted = false;

			@Override
			public void afterCompletion(int status) {
				if (!isCompleted && status == TransactionSynchronization.STATUS_ROLLED_BACK) {
					action.run();
					isCompleted = true;
				}
			}
		});
	}

	private TransactionUtils() {
	}
}
