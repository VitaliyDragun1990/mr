package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Certificate;

@TypeConverter
class CertificateDTOToCertificateTransformer implements Converter<CertificateDTO, Certificate> {

	@Override
	public Certificate convert(CertificateDTO dto) {
		Certificate certificate = new Certificate();
		certificate.setLargeUrl(dto.getLargeUrl());
		certificate.setSmallUrl(dto.getSmallUrl());
		certificate.setName(dto.getName());
		return certificate;
	}

}
