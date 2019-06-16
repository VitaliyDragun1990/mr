package com.revenat.myresume.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.service.ProfileService;

@Controller
@RequestMapping("/profile")
public class ShowProfileController {

	private final ProfileService profileService;

	@Autowired
	public ShowProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/{uid}")
	public String showProfile(@PathVariable("uid") String uid, Model model) {
		ProfileDTO profile = profileService.getByUid(uid);
		if (profile == null) {
			return "profile_not_found";
		}
		model.addAttribute("profile", profile);
		return "profile";
	}

	@GetMapping("/my-profile")
	public String showMyProfile() {
		return "profile";
	}
}
