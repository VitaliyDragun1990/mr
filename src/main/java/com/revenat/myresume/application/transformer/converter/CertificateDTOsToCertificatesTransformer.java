package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.domain.entity.Certificate;

@TypeConverter
class CertificateDTOsToCertificatesTransformer implements Converter<List<CertificateDTO>, List<Certificate>> {
	private CertificateDTOToCertificateTransformer certificateTransformer = new CertificateDTOToCertificateTransformer();
	
	@Override
	public List<Certificate> convert(List<CertificateDTO> source) {
		List<Certificate> certificates = new ArrayList<>();
		if (source != null) {
			for (CertificateDTO dto : source) {
				certificates.add(certificateTransformer.convert(dto));
			}
		}
		return certificates;
	}

}
