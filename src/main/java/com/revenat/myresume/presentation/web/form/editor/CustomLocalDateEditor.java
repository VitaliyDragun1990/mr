package com.revenat.myresume.presentation.web.form.editor;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.revenat.myresume.infrastructure.util.CommonUtils;

/**
 * Custom editor for converting string representation of date into
 * {@link LocalDate} object
 * 
 * @author Vitaliy Dragun
 *
 */
public class CustomLocalDateEditor extends PropertyEditorSupport {

	private final DateTimeFormatter formatter;

	public CustomLocalDateEditor(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	@Override
	public void setAsText(String text) {
		if (CommonUtils.isBlank(text)) {
			setValue(null);
		}
		try {
			setValue(LocalDate.parse(text, formatter));
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Could not parse date: " + e.getMessage(), e);
		}
	}
	
	@Override
		public String getAsText() {
			LocalDate value = (LocalDate) getValue();
			return value != null ? formatter.format(value) : "";
		}
}
