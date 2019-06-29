package com.revenat.myresume.presentation.web.form.converter;

import javax.annotation.Nonnull;

import org.springframework.validation.BindingResult;

public interface FormErrorConverter {
	
	void convertFromErrorToFieldError(@Nonnull Object formInstance, @Nonnull Object validatedInstance,
			@Nonnull BindingResult bindingResult);
}
