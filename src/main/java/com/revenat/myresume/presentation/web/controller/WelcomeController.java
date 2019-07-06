package com.revenat.myresume.presentation.web.controller;

import static com.revenat.myresume.presentation.config.Constants.UI.MAX_PROFILES_PER_PAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.service.profile.SearchProfileService;

@Controller
public class WelcomeController {
	private final SearchProfileService profileService;

	@Autowired
	public WelcomeController(SearchProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping({"/", "/welcome"})
	public String listProfiles(Model model) {
		Page<ProfileDTO> page = profileService.findAll(
				new PageRequest(0, MAX_PROFILES_PER_PAGE, new Sort("firstName", "lastName")));
		model.addAttribute("profiles", page.getContent());
		model.addAttribute("page", page);
		return "welcome";
	}
	
	@GetMapping({"/fragment/more", "/welcome/fragment/more"})
	public String moreProfiles(Model model,
			@PageableDefault(size = MAX_PROFILES_PER_PAGE, sort = {"firstName", "lastName"}) Pageable pageable) {
		Page<ProfileDTO> page = profileService.findAll(pageable);
		model.addAttribute("profiles", page.getContent());
		return "fragment/profile-items";
	}
}

