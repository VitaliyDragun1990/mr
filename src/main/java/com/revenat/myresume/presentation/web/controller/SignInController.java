package com.revenat.myresume.presentation.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.revenat.myresume.presentation.security.AuthenticatedUser;
import com.revenat.myresume.presentation.security.SecurityUtil;

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
		if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") == null) {
			return "redirect:/sign-in";
		}
		return "sign-in";
	}
}
