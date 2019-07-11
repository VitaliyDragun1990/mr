package com.revenat.myresume.infrastructure.gateway.email;

import javax.annotation.Nonnull;

/**
 * Represents email gateway to send email messages
 * 
 * @author Vitaliy Dragun
 *
 */
public interface EmailGateway {

	void sendEmail(@Nonnull String recipientAddress, @Nonnull String recipientName, @Nonnull String subject,
			@Nonnull String content, boolean isHtml);
}
