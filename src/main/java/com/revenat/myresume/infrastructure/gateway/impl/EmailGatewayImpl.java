package com.revenat.myresume.infrastructure.gateway.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.revenat.myresume.infrastructure.exception.EmailGatewayException;
import com.revenat.myresume.infrastructure.gateway.EmailGateway;

@Component
class EmailGatewayImpl implements EmailGateway {
	private final String fromEmail;
	private final String fromName;
	private final JavaMailSender mailSender;
	
	@Autowired
	public EmailGatewayImpl(
			@Value("${email.fromEmail}") String fromEmail,
			@Value("${email.fromName}")String fromName,
			JavaMailSender mailSender) {
		this.fromEmail = fromEmail;
		this.fromName = fromName;
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(String recipientAddress, String recipientName, String subject, String content) {
		try {
			MimeMessage message = buildMessage(recipientAddress, recipientName, subject, content);
			mailSender.send(message);
		} catch (Exception e) {
			throw new EmailGatewayException(e);
		}
	}

	private MimeMessage buildMessage(String recipientAddress, String recipientName, String subject, String content)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), false);
		messageHelper.setSubject(subject);
		messageHelper.setTo(new InternetAddress(recipientAddress, recipientName));
		messageHelper.setFrom(fromEmail, fromName);
		messageHelper.setText(content);
		return new MimeMailMessage(messageHelper).getMimeMessage();
	}

}
