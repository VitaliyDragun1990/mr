package com.revenat.myresume.application.service.impl;

import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.service.NameService;

@Service
class NameServiceImpl implements NameService {
	
	@Override
	public String convertName(String name) {
		if (name.contains("-")) {
			String[] parts = name.split("-");
			return WordUtils.capitalize(parts[0]) + " " + WordUtils.capitalize(parts[1]);
		}
		return WordUtils.capitalize(name);				
	}
}
