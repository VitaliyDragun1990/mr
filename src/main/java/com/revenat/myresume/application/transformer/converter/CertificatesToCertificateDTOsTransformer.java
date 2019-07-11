package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.domain.entity.Certificate;

@TypeConverter
class CertificatesToCertificateDTOsTransformer implements Converter<List<Certificate>, List<CertificateDTO>> {
	
	@Override
	public List<CertificateDTO> convert(List<Certificate> source) {
		List<CertificateDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Certificate c : source) {
				dtoList.add(new CertificateDTO(c));
			}
		}
		return dtoList;
	}

}
