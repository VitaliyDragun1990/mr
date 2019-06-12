package com.revenat.myresume.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {

	@GetMapping("/sign-in")
	public String signIn() {
		return "sing-in";
	}
	
	@GetMapping("/sign-in-failed")
	public String signinFailed() {
		return "sign-in-failed";
	}
}
