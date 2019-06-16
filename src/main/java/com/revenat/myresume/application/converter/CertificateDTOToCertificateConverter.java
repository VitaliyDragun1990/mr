package com.revenat.myresume.application.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
public class CertificateDTOToCertificateConverter implements Converter<CertificateDTO, Certificate> {

	@Override
	public Certificate convert(CertificateDTO dto) {
		Certificate certificate = new Certificate();
		certificate.setId(dto.getId());
		certificate.setLargeUrl(dto.getLargeUrl());
		certificate.setSmallUrl(dto.getSmallUrl());
		certificate.setName(dto.getName());
		if (dto.getProfileId() != null) {
			Profile p = new Profile();
			p.setId(dto.getProfileId());
			certificate.setProfile(p);
		}
		return certificate;
	}

}
