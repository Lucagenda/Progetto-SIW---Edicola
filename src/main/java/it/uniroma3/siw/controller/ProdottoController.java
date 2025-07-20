package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Giocattolo;
import it.uniroma3.siw.model.Giornale;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.*;

@Controller
public class ProdottoController {

	@Autowired
	CredenzialiService credenzialiService;

	@Autowired
	UtenteService utenteService;

	@Autowired
	ProdottoService prodottoService;

	@Autowired
	GiornaleService giornaleService;

	@Autowired
	GiocattoloService giocattoloService;

	@GetMapping("/prodotti")
	public String mostraTuttiProdotti(Model model) {
		model.addAttribute("prodotti", this.prodottoService.getAllProdotti());
		return "prodotti";
	}

	@GetMapping("/prodotto/{id}")
	public String redirectProdotto(@PathVariable("id") Long id) {
		Prodotto prodotto = prodottoService.getProdottoById(id); // carichi il generico

		if (prodotto instanceof Giornale) {
			return "redirect:/giornale/" + id;
		} else if (prodotto instanceof Giocattolo) {
			return "redirect:/giocattolo/" + id;
		}

		return "redirect:/errore"; // pagina di fallback
	}

	@GetMapping("/prodotti/ricerca")
	public String cercaLibri(@RequestParam("nome") String nome, Model model, Authentication authentication) {
		List<Prodotto> tuttiProdotti = prodottoService.cercaPerNome(nome);
		Utente utente = utenteService.getUtente();
		model.addAttribute("credenziali", nome);
		model.addAttribute("prodotti", tuttiProdotti);
		model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));

		return "prodotti";
	}
}
