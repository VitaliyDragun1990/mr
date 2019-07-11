package com.revenat.myresume.presentation.web.form.converter;

import javax.annotation.Nonnull;

import org.springframework.validation.BindingResult;

/**
 * Converts validated instance specific errors into form instance errors to
 * display such errors appropriately on the view.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface FormErrorConverter {

	void convertFromErrorToFieldError(@Nonnull Object formInstance, @Nonnull Object validatedInstance,
			@Nonnull BindingResult bindingResult);
}
