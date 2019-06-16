package com.revenat.myresume.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revenat.myresume.application.service.ProfileService;
import com.revenat.myresume.presentation.form.SkillForm;

@Controller
@RequestMapping("/profile/edit")
public class EditProfileController {
	private final ProfileService profileService;

	@Autowired
	public EditProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping
	public String edit() {
		return "edit";
	}
	
	@GetMapping("/skills")
	public String getEditSKills(Model model) {
		SkillForm skillForm = new SkillForm(profileService.getSkillsFor("amy-fowler"));
		model.addAttribute("skillForm", skillForm);
		return goToSkillsPage(model);
	}
	
	@PostMapping("/skills")
	public String saveEditSkills(@ModelAttribute("skillForm") SkillForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return goToSkillsPage(model);
		}
		// TODO: Update skills
		return "redirect:/profile/amy-fowler";
	}

	private String goToSkillsPage(Model model) {
		model.addAttribute("skillCategories", profileService.getAllSkillCategories());
		return "edit/skills";
	}
}
