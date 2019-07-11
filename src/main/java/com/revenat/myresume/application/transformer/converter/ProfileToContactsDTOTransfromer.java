package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
class ProfileToContactsDTOTransfromer implements Converter<Profile, ContactsDTO> {

	@Override
	public ContactsDTO convert(Profile source) {
		return new ContactsDTO(source.getContacts());
	}

}
