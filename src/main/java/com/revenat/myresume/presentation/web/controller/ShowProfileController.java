package com.revenat.myresume.presentation.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.service.profile.SearchProfileService;
import com.revenat.myresume.presentation.security.service.SecurityUtil;

@Controller
@RequestMapping("/profile")
public class ShowProfileController {

	private final SearchProfileService profileService;

	@Autowired
	public ShowProfileController(SearchProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/{uid}")
	public String findProfile(@PathVariable("uid") String uid, Model model) {
		ProfileDTO profile = profileService.getByUid(uid);
		// TODO: check if profile not found -> show resource-not-found page
		if (!profile.isCompleted()) {
			boolean userNotAuthenticated = SecurityUtil.getAuthenticatedUserId() == null;
			boolean isNotUserProfile = !profile.getId().equals(SecurityUtil.getAuthenticatedUserId());
			if (userNotAuthenticated || isNotUserProfile) {
				return "profile-not-found";
			} else {
				return "redirect:/profile/edit";
			}
		}
		model.addAttribute("profile", profile);
		return "profile";
	}

}
