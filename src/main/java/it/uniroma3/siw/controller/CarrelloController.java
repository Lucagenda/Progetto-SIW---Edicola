package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CarrelloService;
import it.uniroma3.siw.service.GiocattoloService;
import it.uniroma3.siw.service.GiornaleService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class CarrelloController {

	@Autowired
	private CarrelloService carrelloService;

	@Autowired
	private GiornaleService giornaleService;

	@Autowired
	private GiocattoloService giocattoloService;

	@Autowired
	private UtenteService utenteService; // serve a recuperare l'utente loggato, se hai Spring Security

	@GetMapping("/carrello")
	public String visualizzaCarrello(Model model) {
		Utente utente = utenteService.getUtente(); // da implementare
		Carrello carrello = carrelloService.getOrCreateCarrello(utente);
		model.addAttribute("carrello", carrello);
		return "carrello.html";
	}

	@PostMapping("/carrello/aggiungi")
	public String aggiungiProdottoAlCarrello(@RequestParam("tipo") String tipo,
			@RequestParam("id") Long idProdotto,
			Model model) {
		Utente utente = utenteService.getUtente();
		Prodotto prodotto = null;

		if (tipo.equals("giornale")) {
			prodotto = giornaleService.getGiornaleById(idProdotto);
		} else if (tipo.equals("giocattolo")) {
			prodotto = giocattoloService.getGiocattoloById(idProdotto);
		}

		if (prodotto != null) {
			carrelloService.aggiungiProdotto(utente, prodotto);
		}

		return "redirect:/carrello";
	}

	@GetMapping("/carrello/rimuovi/{voceId}")
	public String rimuoviVoce(@PathVariable("voceId") Long voceId) {
		carrelloService.rimuoviVoce(voceId);
		return "redirect:/carrello";
	}
}
