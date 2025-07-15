package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private CredenzialiService credenzialiService;

	private boolean verificaId(Long id) {
		return id == this.utenteService.getUtente().getId();
	}
	
	@GetMapping("/utente/{id}")
	public String showUtente(@PathVariable("id") Long id, @RequestParam(value = "showPasswordModal", required = false, defaultValue = "false")
							boolean showPasswordModal, Model model) {
		if (!verificaId(id))
			return "redirect:/login";
		
		Utente utenteCorrente = utenteService.getUtente();
		// Recupera anche le credenziali dell'utente corrente
		Credenziali credenzialicorrenti = credenzialiService.getCredenziali(id);
		model.addAttribute("utente", utenteCorrente); 
		model.addAttribute("credenziali", credenzialicorrenti);
		model.addAttribute("showPasswordModal", showPasswordModal);
		return "utente/utente.html";
	}
}
