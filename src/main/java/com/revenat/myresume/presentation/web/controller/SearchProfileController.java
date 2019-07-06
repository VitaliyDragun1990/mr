package com.revenat.myresume.presentation.web.controller;

import static com.revenat.myresume.presentation.config.Constants.UI.MAX_PROFILES_PER_PAGE;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
import com.revenat.myresume.application.service.profile.SearchProfileService;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Controller
@RequestMapping("/search")
public class SearchProfileController {
	private final SearchProfileService searchService;

	public SearchProfileController(SearchProfileService searchService) {
		this.searchService = searchService;
	}
	
	@GetMapping
	public String search(@RequestParam(value = "query", required = false) String query, Model model)
			throws UnsupportedEncodingException {
		if (CommonUtils.isBlank(query)) {
			return "redirect:/welcome";
		}
		Page<ProfileDTO> page = searchService.findBySearchQuery(query,
				new PageRequest(0, MAX_PROFILES_PER_PAGE, new Sort("firstName", "lastName")));
		model.addAttribute("profiles", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("query", URLDecoder.decode(query, "UTF-8"));
		return "search-results";
	}
	
	@GetMapping("/fragment/more")
	public String moreProfiles(@RequestParam("query") String query, Model model,
			@PageableDefault(size = MAX_PROFILES_PER_PAGE, sort = {"firstName", "lastName"}) Pageable pageable) {
		Page<ProfileDTO> page = null;
		if (CommonUtils.isNotBlank(query)) {
			page = searchService.findBySearchQuery(query, pageable);
		} else {
			page = searchService.findAll(pageable);
		}
		model.addAttribute("profiles", page.getContent());
		return "fragment/profile-items";
	}
}
