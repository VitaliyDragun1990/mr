package com.revenat.myresume.application.provider.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.revenat.myresume.application.provider.DateTimeProvider;

@Component
class DefaultDateTimeProvider implements DateTimeProvider {

	@Override
	public LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

}
