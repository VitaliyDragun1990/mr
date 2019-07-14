package com.revenat.myresume.domain.document;

import java.io.Serializable;

public abstract class AbstractDocument<T extends Serializable> implements Serializable {
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
		AbstractDocument<T> other = (AbstractDocument<T>) obj;
		return getId() != null &&
				getId().equals(other.getId());
	}
	
	@Override
	public String toString() {
		return String.format("%s[id=%s]", getClass().getSimpleName(), getId());
	}
}
