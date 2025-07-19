package it.uniroma3.siw.controller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.RigaOrdine;
import it.uniroma3.siw.model.VoceCarrello;
import it.uniroma3.siw.constants.StatoOrdine;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.repository.RigaOrdineRepository;


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
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class CarrelloController {

		
	@Autowired
	private CredenzialiService credenzialiService;

	@Autowired
	private CarrelloService carrelloService;

	@Autowired
	private ProdottoService prodottoService;

	@Autowired
	private UtenteService utenteService; // serve a recuperare l'utente loggato, se hai Spring Security

	@GetMapping("/carrello")
	public String visualizzaCarrello(Model model) {
		Utente utente = utenteService.getUtente();
		Carrello carrello = carrelloService.getOrCreateCarrello(utente);
		model.addAttribute("carrello", carrello);
		if (utente != null) {
			model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
		}
		return "carrello.html";
	}

	@PostMapping("/carrello/aggiungi")
	public String aggiungiProdottoAlCarrello(@RequestParam("tipo") String tipo, @RequestParam("id") Long idProdotto, Model model) {
		Utente utente = utenteService.getUtente();
		Prodotto prodotto = prodottoService.getProdottoById(idProdotto);

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

		@Autowired
	private OrdineService ordineService;

	@Autowired
	private RigaOrdineRepository rigaOrdineRepository;
	@PostMapping("/carrello/checkout")
	public String checkout(Model model) {
		Utente utente = utenteService.getUtente();
		Carrello carrello = carrelloService.getOrCreateCarrello(utente);
		if (carrello.getVoci() == null || carrello.getVoci().isEmpty()) {
			model.addAttribute("carrello", carrello);
			model.addAttribute("errore", "Il carrello è vuoto!");
			return "carrello.html";
		}

		Ordine ordine = new Ordine();
		ordine.setUtente(utente);
		ordine.setOrdinante(utente);
		ordine.setDataOrdine(LocalDateTime.now());
		ordine.setStato(StatoOrdine.IN_PREPARAZIONE);

		List<RigaOrdine> righe = new ArrayList<>();
		for (VoceCarrello voce : carrello.getVoci()) {
			RigaOrdine riga = new RigaOrdine();
			riga.setOrdine(ordine);
			riga.setProdotto(voce.getProdotto());
			riga.setQuantita(voce.getQuantita());
			righe.add(riga);
		}
		ordine.setRigheOrdine(righe);
		ordineService.salvaOrdine(ordine);
		rigaOrdineRepository.saveAll(righe);

		// Svuota il carrello eliminando le voci dal database
		List<VoceCarrello> vociDaRimuovere = new ArrayList<>(carrello.getVoci());
		for (VoceCarrello voce : vociDaRimuovere) {
			carrello.getVoci().remove(voce);
			carrelloService.rimuoviVoce(voce.getId());
		}
		carrelloService.getOrCreateCarrello(utente).setVoci(new ArrayList<>());
		carrelloService.getOrCreateCarrello(utente);

		return "redirect:/utente/" + utente.getId();
	}

	@PostMapping("/carrello/svuota")
	public String svuotaCarrello() {
		Utente utente = utenteService.getUtente();
		carrelloService.svuotaCarrello(utente);
		return "redirect:/carrello";
	}
}
