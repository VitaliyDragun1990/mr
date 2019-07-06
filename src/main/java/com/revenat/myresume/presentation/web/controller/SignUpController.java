package com.revenat.myresume.presentation.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revenat.myresume.presentation.security.service.SignUpService;
import com.revenat.myresume.presentation.web.form.SignUpForm;
import com.revenat.myresume.presentation.web.form.converter.FormErrorConverter;

@Controller
@RequestMapping("/sign-up")
public class SignUpController {
	
	private final SignUpService signUpService;
	private final FormErrorConverter errorConverter;
	
	@Autowired
	public SignUpController(SignUpService signUpService, FormErrorConverter errorConverter) {
		this.signUpService = signUpService;
		this.errorConverter = errorConverter;
	}

	@GetMapping
	public String signUp(Model model) {
		model.addAttribute("profileForm", new SignUpForm());
		return "sign-up";
	}
	
	@PostMapping
	public String signUp(@Valid @ModelAttribute("profileForm") SignUpForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			errorConverter.convertFromErrorToFieldError(form, form, bindingResult);
			return "sign-up";
		}
		signUpService.signUp(form.getFirstName(), form.getLastName(), form.getPassword());
		return "redirect:/sign-up/success";
	}
	
	@GetMapping("/success")
	public String signUpSuccess() {
		return "sign-up-success";
	}
}
