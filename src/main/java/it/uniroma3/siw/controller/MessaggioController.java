package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.MessaggioService;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessaggioController {
    @Autowired
    private MessaggioService messaggioService;
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/messaggi")
    public String visualizzaMessaggi(Model model) {
        Utente utente = utenteService.getUtente();
        model.addAttribute("messaggi", messaggioService.getMessaggiPerUtente(utente));
        return "messaggi.html";
    }
}
