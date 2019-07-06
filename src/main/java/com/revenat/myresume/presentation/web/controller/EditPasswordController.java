package com.revenat.myresume.presentation.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revenat.myresume.infrastructure.util.CommonUtils;
import com.revenat.myresume.presentation.security.exception.InvalidRestoreAccessTokenException;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;
import com.revenat.myresume.presentation.security.service.SecurityService;
import com.revenat.myresume.presentation.web.form.PasswordForm;
import com.revenat.myresume.presentation.web.form.RestorePasswordForm;
import com.revenat.myresume.presentation.web.form.converter.FormErrorConverter;

@Controller
@RequestMapping("/profile/edit/password")
public class EditPasswordController {
	
	private static final String[] EMPTY_ARRAY = new String[] {};
	private final SecurityService securityService;
	private final FormErrorConverter formErrorConverter;
	
	@Autowired
	public EditPasswordController(SecurityService securityService, FormErrorConverter formErrorConverter) {
		this.securityService = securityService;
		this.formErrorConverter = formErrorConverter;
	}

	@GetMapping("/update")
	public String getEditPassword(Model model) {
		model.addAttribute("passwordForm", new PasswordForm());
		return "update-password";
	}
	
	@PostMapping("/update")
	public String saveEditPassword(@Valid @ModelAttribute("passwordForm") PasswordForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			formErrorConverter.convertFromErrorToFieldError(form, form, bindingResult);
			return "update-password";
		} else {
			try {
				securityService.updatePassword(authUser, form.getOldPassword(), form.getPassword());
				return "redirect:/profile/" + authUser.getUsername();
			} catch (BadCredentialsException e) {
				bindingResult.addError(buildOldPasswordError());
				return "update-password";
			}
		}
	}
	
	@GetMapping("/restore")
	public String getRestorePassword(Model model, @ModelAttribute("token") String token) {
		if (CommonUtils.isNotBlank(token)) {
			model.addAttribute("passwordForm", new RestorePasswordForm(token));
			return "restore-password";
		} else {
			model.addAttribute("passwordForm", new PasswordForm());
			return "update-password";
		}
	}
	
	@PostMapping("/restore")
	public String saveRestorePassword(@Valid @ModelAttribute("passwordForm") RestorePasswordForm form,
			BindingResult bindingResult, Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			formErrorConverter.convertFromErrorToFieldError(form, form, bindingResult);
			return "restore-password";
		} else {
			try {
				securityService.resetPassword(authUser, form.getToken(), form.getPassword());
			} catch (InvalidRestoreAccessTokenException e) {
				return "restore-failed";
			}
			return "redirect:/profile/" + authUser.getUsername();
		}
	}

	private ObjectError buildOldPasswordError() {
		List<String> codes = new ArrayList<>();
		codes.addAll(Arrays.asList(
				BadCredentialsException.class.getSimpleName() + ".passwordForm.oldPassword",
				"passwordForm.oldPassword",
				"oldPassword"
				));
		return new FieldError("passwordForm", "oldPassword", "", false, codes.toArray(EMPTY_ARRAY), EMPTY_ARRAY, "Provided password is invalid");
	}
}
