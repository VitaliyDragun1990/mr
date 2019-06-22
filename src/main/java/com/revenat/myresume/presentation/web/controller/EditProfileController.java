package com.revenat.myresume.presentation.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.service.profile.EditProfileService;
import com.revenat.myresume.presentation.security.AuthenticatedUser;
import com.revenat.myresume.presentation.security.SecurityUtil;
import com.revenat.myresume.presentation.web.form.CertificateForm;
import com.revenat.myresume.presentation.web.form.CourseForm;
import com.revenat.myresume.presentation.web.form.EducationForm;
import com.revenat.myresume.presentation.web.form.ExperienceForm;
import com.revenat.myresume.presentation.web.form.HobbyForm;
import com.revenat.myresume.presentation.web.form.InfoForm;
import com.revenat.myresume.presentation.web.form.LanguageForm;
import com.revenat.myresume.presentation.web.form.SkillForm;

@Controller
@RequestMapping("/profile/edit")
public class EditProfileController {
	private final EditProfileService profileService;

	@Autowired
	public EditProfileController(EditProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping
	public String getEditMain(Model model) {
		MainInfoDTO mainInfo = profileService.getMainInfoFor(SecurityUtil.getAuthenticatedUserId());
		model.addAttribute("mainInfo", mainInfo);
		return "edit/main-info";
	}
	
	@PostMapping
	public String saveEditMain(@Valid @ModelAttribute("mainInfo") MainInfoDTO mainInfo, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return "edit/main-info";
		}
		profileService.updateMainInfo(SecurityUtil.getAuthenticatedUserId(), mainInfo);
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/contacts")
	public String getEditContacts(Model model) {
		ContactsDTO contacts = profileService.getContactsFor(SecurityUtil.getAuthenticatedUserId());
		model.addAttribute("contacts", contacts);
		return "edit/contacts";
	}
	
	@PostMapping("/contacts")
	public String saveEditContacts(@Valid @ModelAttribute("contacts") ContactsDTO contacts, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return "edit/contacts";
		}
		profileService.updateContacts(SecurityUtil.getAuthenticatedUserId(), contacts);
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/experience")
	public String getEditExperience(Model model) {
		ExperienceForm experienceForm = new ExperienceForm(profileService.getExperienceFor(SecurityUtil.getAuthenticatedUserId()));
		// TODO: deal with allowed date range on the page
		model.addAttribute("experienceForm", experienceForm);
		return "edit/experience";
	}
	
	@PostMapping("/experience")
	public String saveEditExperience(@Valid @ModelAttribute("experienceForm") ExperienceForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return "edit/experience"; 
		}
		profileService.updateExperience(SecurityUtil.getAuthenticatedUserId(), form.getItems());
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/certificates")
	public String getEditCertificates(Model model) {
		CertificateForm certificateForm = 
				new CertificateForm(profileService.getCertificatesFor(SecurityUtil.getAuthenticatedUserId()));
		model.addAttribute("certificateForm", certificateForm);
		return "edit/certificates";
	}
	
	@PostMapping("/certificates")
	public String saveEditCertificates(@Valid @ModelAttribute("certificateForm") CertificateForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return "edit/certificates";
		}
		profileService.updateCertificates(SecurityUtil.getAuthenticatedUserId(), form.getItems());
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@PostMapping("/certificates/upload")
	public void uploadCertificate() {
		// TODO: handle uploading new certificate
	}
	
	@GetMapping("/courses")
	public String getEditCourses(Model model) {
		CourseForm form = new CourseForm(profileService.getCoursesFor(SecurityUtil.getAuthenticatedUserId()));
		// TODO: deal with min year for course ending
		model.addAttribute("courseForm", form);
		return "edit/courses";
	}
	
	@PostMapping("/courses")
	public String saveEditCourses(@Valid @ModelAttribute("courseForm") CourseForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return "edit/courses";
		}
		profileService.updateCourses(SecurityUtil.getAuthenticatedUserId(), form.getItems());
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/education")
	public String getEditEducation(Model model) {
		EducationForm form = new EducationForm(profileService.getEducationFor(SecurityUtil.getAuthenticatedUserId()));
		// TODO: deal with education years ago
		model.addAttribute("educationForm", form);
		return "edit/education";
	}
	
	@PostMapping("/education")
	public String saveEditEducation(@Valid @ModelAttribute("educationForm") EducationForm form,
			BindingResult bindingResult, Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return "edit/education";
		}
		profileService.updateEducation(SecurityUtil.getAuthenticatedUserId(), form.getItems());
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/languages")
	public String getEditLanguages(Model model) {
		LanguageForm form = new LanguageForm(profileService.getLanguagesFor(SecurityUtil.getAuthenticatedUserId()));
		model.addAttribute("languageForm", form);
		return goToLanguagesPage(model);
	}
	
	@PostMapping("/languages")
	public String saveEditLanguages(@Valid @ModelAttribute("languageForm") LanguageForm form,
			BindingResult bindingResult, Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return goToLanguagesPage(model);
		}
		profileService.updateLanguages(SecurityUtil.getAuthenticatedUserId(), form.getItems());
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/hobbies")
	public String getEditHobbies(Model model) {
		HobbyForm form = new HobbyForm(profileService.getHobbiesFor(SecurityUtil.getAuthenticatedUserId()));
		// TODO: deal with max 5 hobbies per profile
		model.addAttribute("hobbyForm", form);
		return "edit/hobbies";
	}
	
	@PostMapping("/hobbies")
	public String saveEditHobbies(@Valid @ModelAttribute("hobbyForm") HobbyForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return "edit/hobbies";
		}
		profileService.updateHobbies(SecurityUtil.getAuthenticatedUserId(), form.getItems());
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/info")
	public String getEditInfo(Model model) {
		InfoForm form = new InfoForm(profileService.getInfoFor(SecurityUtil.getAuthenticatedUserId()));
		model.addAttribute("infoForm", form);
		return "edit/info";
	}
	
	@PostMapping("/info")
	public String saveEditInfo(@Valid @ModelAttribute InfoForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return "edit/info";
		}
		profileService.updateInfoFor(SecurityUtil.getAuthenticatedUserId(), form.getInfo());
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}
	
	@GetMapping("/skills")
	public String getEditSkills(Model model) {
		SkillForm skillForm = new SkillForm(profileService.getSkillsFor(SecurityUtil.getAuthenticatedUserId()));
		model.addAttribute("skillForm", skillForm);
		return goToSkillsPage(model);
	}
	
	@PostMapping("/skills")
	public String saveEditSkills(@Valid @ModelAttribute("skillForm") SkillForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		if (bindingResult.hasErrors()) {
			return goToSkillsPage(model);
		}
		profileService.updateSkills(SecurityUtil.getAuthenticatedUserId(), form.getItems());
		return "redirect:/profile/" + authenticatedUser.getUsername();
	}

	private String goToSkillsPage(Model model) {
		model.addAttribute("skillCategories", profileService.getAllSkillCategories());
		return "edit/skills";
	}
	
	private String goToLanguagesPage(Model model) {
		model.addAttribute("languageTypes", profileService.getLanguageTypes());
		model.addAttribute("languageLevels", profileService.getLanguageLevels());
		return "edit/languages";
	}
}
