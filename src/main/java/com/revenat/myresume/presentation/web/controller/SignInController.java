package com.revenat.myresume.presentation.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.revenat.myresume.presentation.security.model.AuthenticatedUser;
import com.revenat.myresume.presentation.security.service.SecurityUtil;

@Controller
public class SignInController {

	@GetMapping("/sign-in")
	public String signIn() {
		AuthenticatedUser authenticatedUser = SecurityUtil.getAuthenticatedUser();
		if (authenticatedUser != null) {
			return "redirect:/profile/" + authenticatedUser.getUsername();
		}
		return "sign-in";
	}
	
	@GetMapping("/my-profile")
	public String signInSuccess(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/sign-in-failed")
	public String signinFailed(HttpSession session) {
		boolean errorDuringSignIn = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null;
		if (errorDuringSignIn) {
			return "sign-in";
		}
		return "redirect:/sign-in";
	}
}
