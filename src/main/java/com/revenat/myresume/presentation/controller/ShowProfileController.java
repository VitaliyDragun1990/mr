package com.revenat.myresume.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.revenat.myresume.application.service.NameService;

@Controller
public class ShowProfileController {

	private final NameService nameService;
	
	@Autowired
	public ShowProfileController(NameService nameService) {
		this.nameService = nameService;
	}

	@GetMapping("/{uid}")
	public String showProfile(@PathVariable("uid") String uid, Model model) {
		String fullName = nameService.convertName(uid);
		model.addAttribute("name", fullName);
		return "profile";
	}
	
	@GetMapping("/my-profile")
	public String showMyProfile() {
		return "profile";
	}
}
