package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private CredenzialiService credenzialiService;
	

	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credenziali", new Credenziali());
		return "formRegisterUtente";
	}

	@PostMapping(value = { "/register" })
	public String registerUtente(@Valid @ModelAttribute("utente") Utente utente,
			BindingResult utenteBindingResult, @Valid
			@ModelAttribute("credenziali") Credenziali credenziali,
			BindingResult credenzialiBindingResult,
			Model model) {

		// se utente e credenziali hanno entrambi contenuti validi, memorizza Utente e Credenziali nel DB
		if(!utenteBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
			utenteService.saveUtente(utente);
			credenziali.setUtente(utente);
			credenzialiService.saveCredenziali(credenziali);
			model.addAttribute("utente", utente);
			return "registrationSuccessful";
		}
		return "formRegisterUtente";
	}

	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "login";
	}

	@GetMapping(value = "/success")
	public String defaultAfterLogin(Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credentials = credenzialiService.getCredenziali(userDetails.getUsername());
		if (credentials.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
			return "admin/indexAdmin";
		}
		return "index";
	}
}
