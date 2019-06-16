package com.revenat.myresume.application.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.domain.entity.Contacts;

@TypeConverter
public class ContactsDTOToContactsConverter implements Converter<ContactsDTO, Contacts> {

	@Override
	public Contacts convert(ContactsDTO dto) {
		Contacts c = new Contacts();
		c.setFacebook(dto.getFacebook());
		c.setGithub(dto.getGithub());
		c.setLinkedin(dto.getLinkedin());
		c.setSkype(dto.getSkype());
		c.setStackoverflow(dto.getStackoverflow());
		c.setVkontakte(dto.getVkontakte());
		return c;
	}

}
