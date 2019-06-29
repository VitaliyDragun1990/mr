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
import com.revenat.myresume.presentation.web.form.CertificateForm;
import com.revenat.myresume.presentation.web.form.CourseForm;
import com.revenat.myresume.presentation.web.form.EducationForm;
import com.revenat.myresume.presentation.web.form.ExperienceForm;
import com.revenat.myresume.presentation.web.form.HobbyForm;
import com.revenat.myresume.presentation.web.form.InfoForm;
import com.revenat.myresume.presentation.web.form.LanguageForm;
import com.revenat.myresume.presentation.web.form.SkillForm;
import com.revenat.myresume.presentation.web.form.converter.FormErrorConverter;

@Controller
@RequestMapping("/profile/edit")
public class EditProfileController {
	private final EditProfileService profileService;
	private final FormErrorConverter formErrorConverter;

	@Autowired
	public EditProfileController(EditProfileService profileService, FormErrorConverter formErrorConverter) {
		this.profileService = profileService;
		this.formErrorConverter = formErrorConverter;
	}

	@GetMapping
	public String getEditMain(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		MainInfoDTO mainInfo = profileService.getMainInfoFor(authUser.getId());
		model.addAttribute("mainInfo", mainInfo);
		return "edit/main-info";
	}
	
	@PostMapping
	public String saveEditMain(@Valid @ModelAttribute("mainInfo") MainInfoDTO mainInfo, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return "edit/main-info";
		}
		profileService.updateMainInfo(authUser.getId(), mainInfo);
		//TODO: if main info completed -> activate user via set his registration status as completed
		// securityService.completeRegistration(authUser);
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@GetMapping("/contacts")
	public String getEditContacts(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		ContactsDTO contacts = profileService.getContactsFor(authUser.getId());
		model.addAttribute("contacts", contacts);
		return "edit/contacts";
	}
	
	@PostMapping("/contacts")
	public String saveEditContacts(@Valid @ModelAttribute("contacts") ContactsDTO contacts, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return "edit/contacts";
		}
		profileService.updateContacts(authUser.getId(), contacts);
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@GetMapping("/experience")
	public String getEditExperience(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		ExperienceForm experienceForm = new ExperienceForm(profileService.getExperienceFor(authUser.getId()));
		// TODO: deal with allowed date range on the page
		model.addAttribute("experienceForm", experienceForm);
		return "edit/experience";
	}
	
	@PostMapping("/experience")
	public String saveEditExperience(@Valid @ModelAttribute("experienceForm") ExperienceForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			formErrorConverter.convertFromErrorToFieldError(form, form.getItems(), bindingResult);
			return "edit/experience"; 
		}
		profileService.updateExperience(authUser.getId(), form.getItems());
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@GetMapping("/certificates")
	public String getEditCertificates(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		CertificateForm certificateForm = 
				new CertificateForm(profileService.getCertificatesFor(authUser.getId()));
		model.addAttribute("certificateForm", certificateForm);
		return "edit/certificates";
	}
	
	@PostMapping("/certificates")
	public String saveEditCertificates(@Valid @ModelAttribute("certificateForm") CertificateForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return "edit/certificates";
		}
		profileService.updateCertificates(authUser.getId(), form.getItems());
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@PostMapping("/certificates/upload")
	public void uploadCertificate() {
		// TODO: handle uploading new certificate
	}
	
	@GetMapping("/courses")
	public String getEditCourses(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		CourseForm form = new CourseForm(profileService.getCoursesFor(authUser.getId()));
		// TODO: deal with min year for course ending
		model.addAttribute("courseForm", form);
		return "edit/courses";
	}
	
	@PostMapping("/courses")
	public String saveEditCourses(@Valid @ModelAttribute("courseForm") CourseForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return "edit/courses";
		}
		profileService.updateCourses(authUser.getId(), form.getItems());
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@GetMapping("/education")
	public String getEditEducation(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		EducationForm form = new EducationForm(profileService.getEducationFor(authUser.getId()));
		// TODO: deal with education years ago
		model.addAttribute("educationForm", form);
		return "edit/education";
	}
	
	@PostMapping("/education")
	public String saveEditEducation(@Valid @ModelAttribute("educationForm") EducationForm form,
			BindingResult bindingResult, Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			formErrorConverter.convertFromErrorToFieldError(form, form.getItems(), bindingResult);
			return "edit/education";
		}
		profileService.updateEducation(authUser.getId(), form.getItems());
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@GetMapping("/languages")
	public String getEditLanguages(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		LanguageForm form = new LanguageForm(profileService.getLanguagesFor(authUser.getId()));
		model.addAttribute("languageForm", form);
		return goToLanguagesPage(model);
	}
	
	@PostMapping("/languages")
	public String saveEditLanguages(@Valid @ModelAttribute("languageForm") LanguageForm form,
			BindingResult bindingResult, Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return goToLanguagesPage(model);
		}
		profileService.updateLanguages(authUser.getId(), form.getItems());
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@GetMapping("/hobbies")
	public String getEditHobbies(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		HobbyForm form = new HobbyForm(profileService.getHobbiesFor(authUser.getId()));
		// TODO: deal with max 5 hobbies per profile
		model.addAttribute("hobbyForm", form);
		return "edit/hobbies";
	}
	
	@PostMapping("/hobbies")
	public String saveEditHobbies(@Valid @ModelAttribute("hobbyForm") HobbyForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return "edit/hobbies";
		}
		profileService.updateHobbies(authUser.getId(), form.getItems());
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@GetMapping("/info")
	public String getEditInfo(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		InfoForm form = new InfoForm(profileService.getInfoFor(authUser.getId()));
		model.addAttribute("infoForm", form);
		return "edit/info";
	}
	
	@PostMapping("/info")
	public String saveEditInfo(@Valid @ModelAttribute InfoForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return "edit/info";
		}
		profileService.updateInfoFor(authUser.getId(), form.getInfo());
		return "redirect:/profile/" + authUser.getUsername();
	}
	
	@GetMapping("/skills")
	public String getEditSkills(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		SkillForm skillForm = new SkillForm(profileService.getSkillsFor(authUser.getId()));
		model.addAttribute("skillForm", skillForm);
		return goToSkillsPage(model);
	}
	
	@PostMapping("/skills")
	public String saveEditSkills(@Valid @ModelAttribute("skillForm") SkillForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return goToSkillsPage(model);
		}
		// TODO: check for empty cells in form.items list
		profileService.updateSkills(authUser.getId(), form.getItems());
		return "redirect:/profile/" + authUser.getUsername();
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
