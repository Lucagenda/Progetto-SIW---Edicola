package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private CredenzialiService credenzialiService;

	private boolean verificaId(Long id) {
	    return id != null && id.equals(this.utenteService.getUtente().getId());
	}
	
	@GetMapping("/utente/{id}")
	public String showUtente(@PathVariable("id") Long id, @RequestParam(value = "showPasswordModal", required = false, defaultValue = "false")
							boolean showPasswordModal, Model model) {
		if (!verificaId(id))
			return "error";
		
		Utente utenteCorrente = utenteService.getUtente();
		// Recupera anche le credenziali dell'utente corrente
		Credenziali credenzialicorrenti = credenzialiService.getCredenziali(id);
		model.addAttribute("utente", utenteCorrente); 
		model.addAttribute("credenziali", credenzialicorrenti);
		model.addAttribute("showPasswordModal", showPasswordModal);
		// aggiungi lista ordini
		model.addAttribute("ordini", utenteCorrente.getOrdini());
		return "utente/utente.html";
	}
	
	@GetMapping("/utente/{id}/modificaProfilo")
	public String showFormUpdateInfoUser(@PathVariable("id") Long id, Model model) {
		if (!verificaId(id))
			return "redirect:/login";

		Utente utente = this.utenteService.getUtente();
		model.addAttribute("utente", utente);
		return "utente/formModificaUtente.html";
	}

	@PostMapping("/utente/{id}/modificaProfilo")
	public String updateInfoUser(@PathVariable("id") Long id, @ModelAttribute @Valid Utente utente,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("msgError", "Campi non validi");
			model.addAttribute("utente", utente);
			return "utente/formModificaUtente.html";
		}
		if (!verificaId(id))
			return "redirect:/login";
		this.utenteService.saveUtente(utente);
		return "redirect:/utente/" + utente.getId();
	}
	
	@PostMapping("/utente/{id}/cambiaPassword")
	public String updateCredentialsUser(@PathVariable("id") Long id,
	                                    @RequestParam @Valid String confirmPwd,
	                                    @RequestParam @Valid String newPwd,
	                                    Model model) {
	    if (newPwd == null || confirmPwd == null || newPwd.isEmpty() || confirmPwd.isEmpty()) {
	        model.addAttribute("msgError", "La password non può essere vuota");
	    } else if (!newPwd.equals(confirmPwd)) {
	        model.addAttribute("msgError", "Le password non coincidono");
	    } else if (!newPwd.matches("^(?=.*[A-Z])(?=.*[\\W_]).{8,}$")) {
	        model.addAttribute("msgError", "La password deve avere almeno 8 caratteri, una maiuscola e un carattere speciale");
	    } else {
	        // tutto valido, salva
	        Utente utente = this.utenteService.getUtente();
	        if (!verificaId(id)) return "redirect:/login";
	        Credenziali credenziali = this.credenzialiService.getCredenzialiByUtente(utente);
	        credenziali.setPassword(newPwd);
	        this.credenzialiService.saveCredenziali(credenziali);
	        return "redirect:/utente/" + utente.getId();
	    }

	    model.addAttribute("utente", this.utenteService.getUtente());
	    model.addAttribute("showPasswordModal", true);
	    return "utente/utente.html";
	}

}
