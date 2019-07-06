package com.revenat.myresume.presentation.web.form;

import java.io.Serializable;

import com.revenat.myresume.application.validation.annotation.FieldMatch;
import com.revenat.myresume.application.validation.annotation.PasswordStrength;
import com.revenat.myresume.presentation.web.form.annotation.EnableFormErrorConvertation;

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@EnableFormErrorConvertation(
		formName = "passwordForm",
		fieldReference = "confirmPassword",
		validationAnnotationClasses = FieldMatch.class
		)
public class RestorePasswordForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String token;
	
	@PasswordStrength
	private String password;

	private String confirmPassword;
	
	public RestorePasswordForm() {
	}
	
	public RestorePasswordForm(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
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
