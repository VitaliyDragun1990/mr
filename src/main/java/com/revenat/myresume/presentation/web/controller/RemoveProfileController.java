package com.revenat.myresume.presentation.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revenat.myresume.presentation.security.model.AuthenticatedUser;
import com.revenat.myresume.presentation.security.service.SecurityService;

@Controller
@RequestMapping("/profile/remove")
public class RemoveProfileController {

	private final SecurityService securityService;

	public RemoveProfileController(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@GetMapping
	public String showRemovePage() {
		return "remove";
	}
	
	@PostMapping
	public String removeProfile(@AuthenticationPrincipal AuthenticatedUser authUser) {
		securityService.remove(authUser);
		return "redirect:/welcome";
	}
}
