package com.revenat.myresume.domain.entity;

import java.io.Serializable;

public abstract class AbstractEntity<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = -1527821981582779622L;
	
	public abstract T getId();

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		AbstractEntity<T> other = (AbstractEntity<T>) obj;
		return getId() != null &&
				getId().equals(other.getId());
	}
	
	@Override
	public String toString() {
		return String.format("%s[id=%s]", getClass().getSimpleName(), getId());
	}
}
