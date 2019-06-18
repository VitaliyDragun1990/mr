package com.revenat.myresume.application.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.revenat.myresume.application.service.DateTimeProvider;

@Service
class DefaultDateTimeProvider implements DateTimeProvider {

	@Override
	public LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

}
