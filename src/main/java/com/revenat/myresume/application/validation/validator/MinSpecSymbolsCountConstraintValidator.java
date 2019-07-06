package com.revenat.myresume.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.revenat.myresume.application.validation.annotation.MinSpecSymbolsCount;

public class MinSpecSymbolsCountConstraintValidator implements ConstraintValidator<MinSpecSymbolsCount, CharSequence> {
	
	private String symbols;
	private int minSymbolCount;
	
	@Override
	public void initialize(MinSpecSymbolsCount constraintAnnotation) {
		symbols = constraintAnnotation.specSymbols();
		minSymbolCount = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		int symbolCount = 0;
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (symbols.indexOf(ch) != -1) {
				symbolCount++;
			}
		}
		return symbolCount >= minSymbolCount;
	}

}
