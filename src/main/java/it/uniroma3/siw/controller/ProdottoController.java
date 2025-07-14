package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.ProdottoService;

@Controller
public class ProdottoController {
	
	@Autowired
	ProdottoService prodottoService;
	
	@Autowired
	CredenzialiService credenzialiService;
	
	@GetMapping("/prodotti")
    public String mostraTuttiProdotti(Model model) {
        model.addAttribute("prodotti", this.prodottoService.getAllProdotti());
        return "prodotti";
    }

	@GetMapping("/prodotti/ricerca")
	public String cercaLibri(@RequestParam("nome") String nome, Model model, Authentication authentication) {
		List<Prodotto> tuttiProdotti = prodottoService.cercaPerNome(nome);

		model.addAttribute("prodotti", tuttiProdotti);
//		List<String> pathsImmagini = new LinkedList<>();
//		for (Libro libro : tuttiLibri) {
//			if (libro.getImmagini() == null || libro.getImmagini().isEmpty()) {
//				System.out.println("Libro senza immagini: " + libro.getTitolo());
//				continue; // Skip libri without images
//			} else {
//				pathsImmagini.add(libro.getImmagini().get(0).getPath());
//				System.out.println("Path immagine: " + libro.getImmagini().get(0).getPath());
//			}
//
//		}
//		model.addAttribute("copertine", pathsImmagini);

		if (authentication != null) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiService.getCredenziali(username);
			String ruolo = credenziali.getRuolo();

			if (ruolo.equals(Credenziali.ADMIN_ROLE)) {
				return "admin/prodotti";
			}
		}

		return "prodotti";
	}
}
