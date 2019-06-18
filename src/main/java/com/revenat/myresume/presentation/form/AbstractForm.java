package com.revenat.myresume.presentation.form;

import java.io.Serializable;

import com.revenat.myresume.infrastructure.util.CommonUtils;

abstract class AbstractForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
