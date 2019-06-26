package com.revenat.myresume.presentation.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revenat.myresume.infrastructure.util.CommonUtils;
import com.revenat.myresume.presentation.security.AuthenticatedUser;
import com.revenat.myresume.presentation.security.SocialSignInService;

@Controller
public class SocialSignInController {
	private final SocialSignInService signinService;

	@Autowired
	public SocialSignInController(SocialSignInService signinService) {
		this.signinService = signinService;
	}
	
	@GetMapping("/fbLogin")
	public String goToFacebook() {
		return "redirect:" + signinService.getAuthorizeUrl();
	}
	
	@GetMapping("/social-signin-fb")
	public String faceBookSignIn(@RequestParam(value="code", required = false) String code) {
		if (CommonUtils.isBlank(code)) {
			return "redirect:/sign-in";
		}
		AuthenticatedUser authenticatedUser = signinService.signIn(code);
		if (authenticatedUser.isRegistrationCompleted()) {
			return "redirect:/" + authenticatedUser.getUsername();
		} else {
			return "redirect:/profile/edit";
		}
	}
}
