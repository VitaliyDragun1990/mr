package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Profile;

@Deprecated
@TypeConverter
class ProfileToCertificateDTOsTransformer implements Converter<Profile, List<CertificateDTO>> {

	@Override
	public List<CertificateDTO> convert(Profile profile) {
		List<CertificateDTO> dtoList = new ArrayList<>();
		for (Certificate certificate : profile.getCertificates()) {
			dtoList.add(new CertificateDTO(certificate));
		}
		return dtoList;
	}

}
