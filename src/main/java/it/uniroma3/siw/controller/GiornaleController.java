
package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Giornale;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.GiornaleService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
public class GiornaleController {
	

	@Autowired
	GiornaleService giornaleService;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	CredenzialiService credenzialiService;
	
	
	@GetMapping("/modificaGiornale/{id}")
	public String formModificaGiornale(@PathVariable("id") Long id, Model model) {
		Giornale giornale = giornaleService.getGiornaleById(id);
		model.addAttribute("giornale", giornale);
		Utente utente = utenteService.getUtente();
		if (utente != null) {
			model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
		}
		return "modificaGiornale";
	}

	@PostMapping("/modificaGiornale/{id}")
	public String modificaGiornale(@PathVariable("id") Long id,
								   @ModelAttribute("giornale") Giornale giornaleModificato,
								   BindingResult bindingResult,
								   Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("giornale", giornaleModificato);
			model.addAttribute("messaggioErrore", "Compila tutti i campi correttamente");
			return "modificaGiornale";
		}
		Giornale giornale = giornaleService.getGiornaleById(id);
		giornale.setNome(giornaleModificato.getNome());
		giornale.setAnno(giornaleModificato.getAnno());
		giornale.setPrezzo(giornaleModificato.getPrezzo());
		giornale.setPeriodicita(giornaleModificato.getPeriodicita());
		giornale.setDirettore(giornaleModificato.getDirettore());
		giornaleService.save(giornale);
		return "redirect:/admin/aggiornaGiornale";
	}

	@GetMapping("/giornali")
	public String mostraTuttiGiornali(Model model) {
		Utente utente = utenteService.getUtente();
		model.addAttribute("giornali", this.giornaleService.getAllGiornali());
		if (utente != null) {
			model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
		}
		return "giornali.html";
	}

	@GetMapping("/giornale/{id}")
	public String getGiornale(@PathVariable("id") Long id, Model model) {
		model.addAttribute("giornale", this.giornaleService.getGiornaleById(id));
		// Recupera utente loggato
		Utente utente = utenteService.getUtente();
		if (utente != null) {
			model.addAttribute("credenziali", credenzialiService.getCredenzialiByUtente(utente));
		}
		return "giornale.html";
	}
	
	@GetMapping("/admin/formNewGiornale")
	public String formNewGiornale(Model model) {
		Utente utente = utenteService.getUtente();
		if (utente != null) {
			model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
		}
		model.addAttribute("giornale", new Giornale());
		return "formNewGiornale.html";
	}

	@PostMapping("/giornale")
	public String newGiornale(@Valid @ModelAttribute("giornale") Giornale giornale,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {         //Sono emersi errori nel binding
			model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
			return "formNewGiornale.html";
		} else {
			this.giornaleService.save(giornale);
			model.addAttribute("giornale", giornale);
			return "redirect:/giornale/"+giornale.getId();
		}
	}

	@GetMapping("/admin/aggiornaGiornale")
	public String homeAggiornaGiornale(Model model) {
		List<Giornale> giornali = new java.util.ArrayList<>();
		for (Giornale g : this.giornaleService.getAllGiornali()) {
			giornali.add(g);
		}
		giornali.sort(Comparator.comparingLong(Giornale::getId));
		model.addAttribute("giornali", giornali);
		Utente utente = utenteService.getUtente();
		if (utente != null) {
			model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
		}
		return "aggiornaGiornale";
	}

	@GetMapping("/cancellaGiornale/{id}")
	public String deleteGiornale(@PathVariable("id") Long id, Model model) {
		this.giornaleService.deleteById(id);
		return "redirect:/aggiornaGiornale";
	}
	
}
