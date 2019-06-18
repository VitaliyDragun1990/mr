package com.revenat.myresume.application.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.revenat.myresume.application.validation.annotation.EnglishLanguage;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.infrastructure.util.CommonUtils;

public class CertificateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank
	@Size(max = 50)
	@EnglishLanguage
	private String name;
	@NotBlank
	@Size(max = 255)
	private String largeUrl;
	@NotBlank
	@Size(max = 255)
	private String smallUrl;
	
	public CertificateDTO() {}
	
	public CertificateDTO(Certificate entity) {
		this.id = entity.getId();
		this.largeUrl = entity.getLargeUrl();
		this.name = entity.getName();
		this.smallUrl = entity.getSmallUrl();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLargeUrl() {
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((largeUrl == null) ? 0 : largeUrl.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((smallUrl == null) ? 0 : smallUrl.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CertificateDTO))
			return false;
		CertificateDTO other = (CertificateDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (largeUrl == null) {
			if (other.largeUrl != null)
				return false;
		} else if (!largeUrl.equals(other.largeUrl))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (smallUrl == null) {
			if (other.smallUrl != null)
				return false;
		} else if (!smallUrl.equals(other.smallUrl))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
