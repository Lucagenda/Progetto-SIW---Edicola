package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.constants.StatoOrdine;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.service.UtenteService;

import java.util.Arrays;

@Controller
public class AdminController {
	
	@Autowired
	private CredenzialiService credenzialiService;
	
    @Autowired
    private OrdineService ordineService;
    
    @Autowired
	private UtenteService utenteService;

    @GetMapping("/admin/ordini")
    public String listaOrdini(Model model) {
    	Utente utente = utenteService.getUtente();
        model.addAttribute("ordini", ordineService.getAllOrdini());
        model.addAttribute("stati", Arrays.asList(StatoOrdine.values()));
        model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
        return "admin/ordiniAdmin";
    }

    @PostMapping("/admin/ordini/{id}/stato")
    public String aggiornaStatoOrdine(@PathVariable("id") Long id, @RequestParam("stato") StatoOrdine stato) {
        ordineService.aggiornaStatoOrdine(id, stato);
        return "redirect:/admin/ordini";
    }

}
