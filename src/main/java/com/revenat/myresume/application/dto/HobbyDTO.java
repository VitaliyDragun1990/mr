package com.revenat.myresume.application.dto;

import java.io.Serializable;

import com.revenat.myresume.domain.entity.Hobby;
import com.revenat.myresume.infrastructure.util.CommonUtil;

/**
 * @author Vitaliy Dragun
 *
 */
public class HobbyDTO implements Serializable, Comparable<HobbyDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private String name;
	private boolean selected;
	
	public HobbyDTO() {
	}
	
	public HobbyDTO(String name) {
		this.name = name;
	}

	public HobbyDTO(String name, boolean selected) {
		this.name = name;
		this.selected = selected;
	}
	
	public HobbyDTO(Hobby entity, Long profileId) {
		this.id = entity.getId();
		this.profileId = profileId;
		this.name = entity.getName();
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String getCssClassName() {
		return name.replace(" ", "-").toLowerCase();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HobbyDTO))
			return false;
		HobbyDTO other = (HobbyDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

	@Override
	public int compareTo(HobbyDTO o) {
		if(o == null || getName() == null) {
			return 1;
		} else{
			return getName().compareTo(o.getName());
		}
	}
	
	@Override
	public String toString() {
		return CommonUtil.toString(this);
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
}
