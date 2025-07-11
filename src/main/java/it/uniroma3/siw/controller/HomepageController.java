package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.service.CredenzialiService;

@Controller
public class HomepageController {

    @Autowired
    private CredenzialiService credenzialiService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            // Ottieni le credenziali a partire dallo username
            Credenziali credenziali = credenzialiService.getCredenziali(principal.getName());

            // Passale al template
            model.addAttribute("credenziali", credenziali);
        }

        return "index.html"; // oppure "index" se usi ViewResolver con .html incluso
    }
}
