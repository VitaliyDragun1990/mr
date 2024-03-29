package com.revenat.myresume.application.service.notification;

/**
 * This component represents notification message that can be sent from
 * application.
 * 
 * @author Vitaliy Dragun
 *
 */
public class NotificationMessage {
	private String destinationAddress;
	private String destinationName;
	private String subject;
	private String content;

	public NotificationMessage() {
	}

	public NotificationMessage(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
