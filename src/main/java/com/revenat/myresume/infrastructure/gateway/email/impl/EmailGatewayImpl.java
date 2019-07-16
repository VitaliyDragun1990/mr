package com.revenat.myresume.infrastructure.gateway.email.impl;

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
import com.revenat.myresume.infrastructure.gateway.email.EmailGateway;
import com.revenat.myresume.infrastructure.util.Checks;

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
	public void sendEmail(String recipientAddress, String recipientName, String subject, String content, boolean isHtml) {
		checkParams(recipientAddress, recipientName, subject, content);
		
		try {
			MimeMessage message = buildMessage(recipientAddress, recipientName, subject, content, isHtml);
			mailSender.send(message);
		} catch (Exception e) {
			throw new EmailGatewayException(e);
		}
	}

	private MimeMessage buildMessage(String recipientAddress, String recipientName, String subject, String content, boolean isHtml)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), false);
		messageHelper.setSubject(subject);
		messageHelper.setTo(new InternetAddress(recipientAddress, recipientName));
		messageHelper.setFrom(fromEmail, fromName);
		messageHelper.setText(content, isHtml);
		return new MimeMailMessage(messageHelper).getMimeMessage();
	}
	
	private static void checkParams(String recipientAddress, String recipientName, String subject, String content) {
		Checks.checkParam(recipientAddress != null, "recipientAddress to send email to can not be null");
		Checks.checkParam(recipientName != null, "recipientName to send email to can not be null");
		Checks.checkParam(subject != null, "subject of the email to send can not be null");
		Checks.checkParam(content != null, "content of the email to send can not be null");
	}

}
