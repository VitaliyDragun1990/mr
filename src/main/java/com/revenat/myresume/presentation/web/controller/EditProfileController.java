package com.revenat.myresume.presentation.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.exception.DTOValidationException;
import com.revenat.myresume.application.service.profile.EditProfileService;
import com.revenat.myresume.presentation.image.model.UploadedCertificateResult;
import com.revenat.myresume.presentation.image.model.UploadedImageResult;
import com.revenat.myresume.presentation.image.service.ImageUploaderService;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;
import com.revenat.myresume.presentation.web.form.CertificateForm;
import com.revenat.myresume.presentation.web.form.CourseForm;
import com.revenat.myresume.presentation.web.form.EducationForm;
import com.revenat.myresume.presentation.web.form.ExperienceForm;
import com.revenat.myresume.presentation.web.form.InfoForm;
import com.revenat.myresume.presentation.web.form.LanguageForm;
import com.revenat.myresume.presentation.web.form.SkillForm;
import com.revenat.myresume.presentation.web.form.converter.FormErrorConverter;
import com.revenat.myresume.presentation.web.form.editor.CustomLocalDateEditor;
import com.revenat.myresume.presentation.web.service.StaticDataService;

@Controller
@RequestMapping("/profile/edit")
public class EditProfileController {
	private final EditProfileService profileService;
	private final FormErrorConverter formErrorConverter;
	private final ImageUploaderService uploaderService;
	private final StaticDataService staticDataService;
	private final int maxHobbiesPerProfle;

	@Autowired
	public EditProfileController(EditProfileService profileService, FormErrorConverter formErrorConverter,
			ImageUploaderService uploaderService, StaticDataService staticDataService,
			@Value("${profile.hobbies.max}") int maxHobbiesPerProfile) {
		this.profileService = profileService;
		this.formErrorConverter = formErrorConverter;
		this.uploaderService = uploaderService;
		this.staticDataService = staticDataService;
		this.maxHobbiesPerProfle = maxHobbiesPerProfile;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(LocalDate.class, new CustomLocalDateEditor("yyyy-MM-dd"));
	}

	@GetMapping
	public String getEditMain(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		MainInfoDTO mainInfo = profileService.getMainInfoFor(authUser.getId());
		model.addAttribute("mainInfo", mainInfo);
		return "edit/main-info";
	}
	
	@PostMapping
	public String saveEditMain(@Valid @ModelAttribute("mainInfo") MainInfoDTO mainInfo, BindingResult bindingResult,
			@RequestParam("profilePhoto") MultipartFile uploadedPhoto, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return "edit/main-info";
		}
		
		if (!uploadedPhoto.isEmpty()) {
			UploadedImageResult uploadedResult = uploaderService.uploadNewProfilePhoto(uploadedPhoto);
			mainInfo.setLargePhoto(uploadedResult.getLargeUrl());
			mainInfo.setSmallPhoto(uploadedResult.getSmallUrl());
		}
		
		try {
			profileService.updateMainInfo(authUser.getId(), mainInfo);
			return "redirect:/profile/edit/contacts";
		} catch (DTOValidationException e) {
			bindingResult.addError(e.buildFieldError("mainInfo"));
			return "edit/main-info";
		}
	}
	
	@GetMapping("/contacts")
	public String getEditContacts(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		ContactsDTO contacts = profileService.getContactsFor(authUser.getId());
		model.addAttribute("contactsForm", contacts);
		return "edit/contacts";
	}
	
	@PostMapping("/contacts")
	public String saveEditContacts(@Valid @ModelAttribute("contactsForm") ContactsDTO contacts, BindingResult bindingResult,
			@AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return "edit/contacts";
		}
		profileService.updateContacts(authUser.getId(), contacts);
		return "redirect:/profile/edit/skills";
	}
	
	@GetMapping("/skills")
	public String getEditSkills(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		model.addAttribute("skillForm", new SkillForm(profileService.getSkillsFor(authUser.getId())));
		return goToSkillsPage(model);
	}
	
	@PostMapping("/skills")
	public String saveEditSkills(@Valid @ModelAttribute("skillForm") SkillForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return goToSkillsPage(model);
		}
		profileService.updateSkills(authUser.getId(), form.getItems());
		return "redirect:/profile/edit/experience";
	}
	
	@GetMapping("/experience")
	public String getEditExperience(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		ExperienceForm experienceForm = new ExperienceForm(profileService.getExperienceFor(authUser.getId()));
		model.addAttribute("experienceForm", experienceForm);
		return goToExperiencesPage(model);
	}
	
	@PostMapping("/experience")
	public String saveEditExperience(@Valid @ModelAttribute("experienceForm") ExperienceForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			formErrorConverter.convertFromErrorToFieldError(form, form.getItems(), bindingResult);
			return goToExperiencesPage(model); 
		}
		profileService.updateExperience(authUser.getId(), form.getItems());
		return "redirect:/profile/edit/certificates";
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
		profileService.updateCertificates(authUser.getId(), form.getItems(), uploaderService::clearTemporaryResources);
		return "redirect:/profile/edit/courses";
	}
	
	@PostMapping("/certificates/upload")
	public @ResponseBody UploadedCertificateResult uploadCertificate(@RequestParam("certificateFile") MultipartFile certificateFile) {
		return uploaderService.uploadNewCertificateImage(certificateFile);
	}
	
	@GetMapping("/courses")
	public String getEditCourses(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		model.addAttribute("courseForm", new CourseForm(profileService.getCoursesFor(authUser.getId())));
		return goToCoursesPage(model);
	}

	@PostMapping("/courses")
	public String saveEditCourses(@Valid @ModelAttribute("courseForm") CourseForm form, BindingResult bindingResult,
			Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return goToCoursesPage(model);
		}
		profileService.updateCourses(authUser.getId(), form.getItems());
		return "redirect:/profile/edit/education";
	}
	
	@GetMapping("/education")
	public String getEditEducation(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		model.addAttribute("educationForm", new EducationForm(profileService.getEducationFor(authUser.getId())));
		return goToEducationPage(model);
	}

	@PostMapping("/education")
	public String saveEditEducation(@Valid @ModelAttribute("educationForm") EducationForm form,
			BindingResult bindingResult, Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			formErrorConverter.convertFromErrorToFieldError(form, form.getItems(), bindingResult);
			return goToEducationPage(model);
		}
		profileService.updateEducation(authUser.getId(), form.getItems());
		return "redirect:/profile/edit/languages";
	}
	
	@GetMapping("/languages")
	public String getEditLanguages(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		model.addAttribute("languageForm", new LanguageForm(profileService.getLanguagesFor(authUser.getId())));
		return goToLanguagesPage(model);
	}
	
	@PostMapping("/languages")
	public String saveEditLanguages(@Valid @ModelAttribute("languageForm") LanguageForm form,
			BindingResult bindingResult, Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		if (bindingResult.hasErrors()) {
			return goToLanguagesPage(model);
		}
		profileService.updateLanguages(authUser.getId(), form.getItems());
		return "redirect:/profile/edit/hobbies";
	}
	
	@GetMapping("/hobbies")
	public String getEditHobbies(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		model.addAttribute("hobbies",
				staticDataService.listAllHobbiesWithSelectedMarked(profileService.getHobbiesFor(authUser.getId())));
		return "edit/hobbies";
	}
	
	@PostMapping("/hobbies")
	public String saveEditHobbies(@RequestParam("hobbies") List<String> hobbies, Model model,
			@AuthenticationPrincipal AuthenticatedUser authUser) {
		List<HobbyDTO> selectedHobbies = staticDataService.createHobbyListByNames(hobbies);
		if (selectedHobbies.size() > maxHobbiesPerProfle) {
			model.addAttribute("hobbies",staticDataService.listAllHobbiesWithSelectedMarked(selectedHobbies));
			model.addAttribute("hobbiesLimitExcedeed", Boolean.TRUE);
			return "edit/hobbies";
			
		}
		profileService.updateHobbies(authUser.getId(), selectedHobbies);
		return "redirect:/profile/edit/info";
	}
	
	@GetMapping("/info")
	public String getEditInfo(Model model, @AuthenticationPrincipal AuthenticatedUser authUser) {
		model.addAttribute("infoForm", new InfoForm(profileService.getInfoFor(authUser.getId())));
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

	private String goToSkillsPage(Model model) {
		model.addAttribute("skillCategories", staticDataService.getAllSkillCategories());
		return "edit/skills";
	}
	
	private String goToExperiencesPage(Model model) {
		model.addAttribute("years", staticDataService.listExperienceYears());
		model.addAttribute("months", staticDataService.mapMonths());
		return "edit/experience";
	}
	
	private String goToCoursesPage(Model model) {
		model.addAttribute("years", staticDataService.listCoursesYears());
		model.addAttribute("months", staticDataService.mapMonths());
		return "edit/courses";
	}
	
	
	private String goToEducationPage(Model model) {
		model.addAttribute("years", staticDataService.listEducationYears());
		model.addAttribute("months", staticDataService.mapMonths());
		return "edit/education";
	}
	
	private String goToLanguagesPage(Model model) {
		model.addAttribute("languageTypes", staticDataService.getLanguageTypes());
		model.addAttribute("languageLevels", staticDataService.getLanguageLevels());
		return "edit/languages";
	}
}
