package com.revenat.myresume.application.service.impl;

import org.springframework.stereotype.Service;

import com.revenat.myresume.application.service.NameService;

@Service
class NameServiceImpl implements NameService {
	
	@Override
	public String convertName(String name) {
		return name.toUpperCase();				
	}
}
