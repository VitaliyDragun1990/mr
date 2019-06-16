package com.revenat.myresume.application.dto;

import java.io.Serializable;

import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.infrastructure.util.CommonUtil;

public class CertificateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private String name;
	private String largeUrl;
	private String smallUrl;
	
	public CertificateDTO() {}
	
	public CertificateDTO(Certificate entity, Long prodileId) {
		this.id = entity.getId();
		this.profileId = prodileId;
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

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
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
		int result = super.hashCode();
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
		if (!super.equals(obj))
			return false;
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
		return CommonUtil.toString(this);
	}
}
