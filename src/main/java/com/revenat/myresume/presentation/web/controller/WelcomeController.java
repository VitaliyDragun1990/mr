package com.revenat.myresume.presentation.web.controller;

import java.io.UnsupportedEncodingException;

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
import com.revenat.myresume.application.service.SearchProfileService;
import com.revenat.myresume.presentation.config.Constants;

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
				new PageRequest(0, Constants.MAX_PROFILES_PER_PAGE, new Sort("firstName", "lastName")));
		model.addAttribute("profiles", page.getContent());
		model.addAttribute("page", page);
		return "profiles";
	}
	
	@GetMapping({"/fragment/more", "/welcome/fragment/more"})
	public String moreProfiles(Model model,
			@PageableDefault(size = Constants.MAX_PROFILES_PER_PAGE, sort = {"firstName", "lastName"}) Pageable pageable)
	throws UnsupportedEncodingException {
		Page<ProfileDTO> page = profileService.findAll(pageable);
		model.addAttribute("profiles", page.getContent());
		return "fragment/profile-items";
	}
}

