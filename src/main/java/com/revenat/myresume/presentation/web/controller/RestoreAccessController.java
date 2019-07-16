package com.revenat.myresume.presentation.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.revenat.myresume.presentation.security.exception.InvalidRestoreAccessTokenException;
import com.revenat.myresume.presentation.security.service.SecurityService;

@Controller
@RequestMapping("/restore")
public class RestoreAccessController {
	
	private final SecurityService securityService;
	
	@Autowired
	public RestoreAccessController(SecurityService securityService) {
		this.securityService = securityService;
	}

	@GetMapping
	public String getRestoreAccess() {
		return "restore";
	}
	
	@PostMapping
	public String processRestoreAccess(@RequestParam("uid") String anyUniqueId) {
		securityService.restoreAccess(anyUniqueId);
		return "redirect:/restore/success";
	}
	
	@GetMapping("/success")
	public String getRestoreSuccess() {
		return "restore-success";
	}
	
	@GetMapping("/{token}")
	public String restoreAccess(@PathVariable("token") String token, RedirectAttributes redirectAttributes) {
		try {
			securityService.verifyPasswordResetToken(token);
		} catch (InvalidRestoreAccessTokenException e) {
			return "restore-failed";
		}
		redirectAttributes.addFlashAttribute("token", token);
		return "redirect:/profile/edit/password/restore";
	}
}
