package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Certificate;
import com.revenat.myresume.domain.document.Profile;

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
