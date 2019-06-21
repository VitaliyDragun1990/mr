package com.revenat.myresume.presentation.web.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.service.SearchProfileService;
import com.revenat.myresume.presentation.config.Constants;

@Controller
@RequestMapping("/search")
public class SearchProfileController {
	private final SearchProfileService searchService;

	public SearchProfileController(SearchProfileService searchService) {
		this.searchService = searchService;
	}
	
	@GetMapping
	public String search(@RequestParam("query") String query, Model model) {
		Page<ProfileDTO> page = searchService.findBySearchQuery(query,
				new PageRequest(0, Constants.MAX_PROFILES_PER_PAGE, new Sort("firstName", "lastName")));
		model.addAttribute("profiles", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		return "profiles";
	}
	
	@GetMapping("/fragment/more")
	public String moreProfiles(@RequestParam("query") String query, Model model,
			@PageableDefault(size = Constants.MAX_PROFILES_PER_PAGE, sort = {"firstName", "lastName"}) Pageable pageable)
	throws UnsupportedEncodingException {
		Page<ProfileDTO> page = searchService.findBySearchQuery(query, pageable);
		model.addAttribute("profiles", page.getContent());
		return "fragment/profile-items";
	}
}
