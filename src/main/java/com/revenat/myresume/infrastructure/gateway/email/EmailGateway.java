package com.revenat.myresume.infrastructure.gateway.email;

public interface EmailGateway {

	void sendEmail(String recipientAddress, String recipientName, String subject, String content);
}
