package com.revenat.myresume.presentation.web.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.revenat.myresume.application.validation.annotation.EnglishLanguage;
import com.revenat.myresume.application.validation.annotation.FieldMatch;
import com.revenat.myresume.application.validation.annotation.PasswordStrength;

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class SignUpForm extends AbstractForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 50)
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	private String firstName;
	
	@NotBlank
	@Size(max = 50)
	@EnglishLanguage(withNumbers = false, withSpecSymbols = false)
	private String lastName;
	
	@PasswordStrength
	private String password;
	
	private String confirmPassword;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
