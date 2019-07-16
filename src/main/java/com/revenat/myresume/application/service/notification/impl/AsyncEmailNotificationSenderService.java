package com.revenat.myresume.application.service.notification.impl;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.service.notification.NotificationMessage;
import com.revenat.myresume.application.service.notification.NotificationSenderService;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.infrastructure.exception.EmailGatewayException;
import com.revenat.myresume.infrastructure.gateway.email.EmailGateway;
import com.revenat.myresume.infrastructure.util.Checks;

/**
 * Implementation of {@link NotificationSenderService} which uses email to send
 * notification messages.
 * 
 * @author Vitaliy Dragun
 *
 */
@Service
class AsyncEmailNotificationSenderService implements NotificationSenderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEmailNotificationSenderService.class);

	private final EmailGateway emailGateway;
	private final ExecutorService executorService;
	private final int tryAttempts;
	private final boolean production;

	@Autowired
	public AsyncEmailNotificationSenderService(
			EmailGateway emailGateway,
			@Qualifier("defaultExecutorService") ExecutorService executorService,
			@Value("${email.sendTryAttempts}") int tryAttempts,
			@Value("${app.production}") boolean production) {
		this.emailGateway = emailGateway;
		this.executorService = executorService;
		this.tryAttempts = tryAttempts;
		this.production = production;
	}

	@Override
	public void sendNotification(NotificationMessage message) {
		Checks.checkParam(message != null, "message to send notificaiton with can not be null");
		
		executorService.submit(new EmailItem(message, tryAttempts));
	}

	@Override
	public String getDestinationAddress(Profile profile) {
		Checks.checkParam(profile != null, "profile to get destination address for can not be null");
		
		return profile.getEmail();
	}

	private class EmailItem implements Runnable {
		private final NotificationMessage message;
		private int tryAttemptsCount;

		private EmailItem(NotificationMessage message, int tryAttemptsCount) {
			this.message = message;
			this.tryAttemptsCount = tryAttemptsCount;
		}

		@Override
		public void run() {
			try {
				sendEmail();
			} catch (EmailGatewayException e) {
				handleException(e);
			}
		}
		
		private void sendEmail() {
			LOGGER.debug("Sending new email message to {}", message.getDestinationAddress());
			if (production) {
				emailGateway.sendEmail(message.getDestinationAddress(), message.getDestinationName(),
						message.getSubject(), message.getContent(), true);
				LOGGER.debug("Email message to {} has been successfully sent",
						message.getDestinationAddress());
			} else {
				LOGGER.warn("DEMO MODE: Email to {}, {}/{}", message.getDestinationAddress(), message.getSubject(), message.getContent());
			}
		}

		private void handleException(EmailGatewayException e) {
			LOGGER.error("Can't send email to " + message.getDestinationAddress() + ": " + e.getMessage(), e);
			tryAttemptsCount--;
			if (tryAttemptsCount > 0) {
				LOGGER.debug(
						"Decremented tryAttempts counter and trying to send email again: tryAttempts={}, recipientAddres={}",
						tryAttemptsCount, message.getDestinationAddress());
				executorService.submit(this);
			} else {
				LOGGER.error("Email has not been sent to {}", message.getDestinationAddress());
			}
		}
	}
}
