package it.uniroma3.siw.controller;

import java.util.Comparator;
import java.util.List;
import it.uniroma3.siw.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Giocattolo;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.GiocattoloService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class GiocattoloController {

    @Autowired
    GiocattoloService giocattoloService;

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private CredenzialiService credenzialiService;
    
    @GetMapping("/modificaGiocattolo/{id}")
    public String formModificaGiocattolo(@PathVariable("id") Long id, Model model) {
        Giocattolo giocattolo = giocattoloService.getGiocattoloById(id);
        model.addAttribute("giocattolo", giocattolo);
        Utente utente = utenteService.getUtente();
        if (utente != null) {
            model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
        }
        return "modificaGiocattolo";
    }

    @PostMapping("/modificaGiocattolo/{id}")
    public String modificaGiocattolo(@PathVariable("id") Long id,
                                     @ModelAttribute("giocattolo") Giocattolo giocattoloModificato,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("giocattolo", giocattoloModificato);
            model.addAttribute("messaggioErrore", "Compila tutti i campi correttamente");
            return "modificaGiocattolo";
        }
        Giocattolo giocattolo = giocattoloService.getGiocattoloById(id);
        giocattolo.setNome(giocattoloModificato.getNome());
        giocattolo.setProduttore(giocattoloModificato.getProduttore());
        giocattolo.setTipologia(giocattoloModificato.getTipologia());
        giocattolo.setPrezzo(giocattoloModificato.getPrezzo());
        giocattoloService.save(giocattolo);
        return "redirect:/admin/aggiornaGiocattolo";
    }
    
    @GetMapping("/giocattoli")
    public String mostraTuttiGiocattoli(Model model) {
        Utente utente = utenteService.getUtente();
        model.addAttribute("giocattoli", this.giocattoloService.getAllGiocattoli());
        if (utente != null) {
            model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
        }
        return "giocattoli.html";
    }

    @GetMapping("/giocattolo/{id}")
    public String getGiocattolo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("giocattolo", this.giocattoloService.getGiocattoloById(id));
        // Recupera utente loggato
        Utente utente = utenteService.getUtente();
        if (utente != null) {
            model.addAttribute("credenziali", credenzialiService.getCredenzialiByUtente(utente));
        }
        return "giocattolo.html";
    }

    @GetMapping("/admin/formNewGiocattolo")
    public String formNewGiocattolo(Model model) {
        model.addAttribute("giocattolo", new Giocattolo());
        return "formNewGiocattolo.html";
    }

    @PostMapping("/giocattolo")
    public String newGiocattolo(@Valid @ModelAttribute("giocattolo") Giocattolo giocattolo, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
            return "formNewGiocattolo.html";
        } else {
            this.giocattoloService.save(giocattolo);
            model.addAttribute("giocattolo", giocattolo);
            return "redirect:/giocattolo/" + giocattolo.getId();
        }
    }

    @GetMapping("/admin/aggiornaGiocattolo")
    public String homeAggiornaGiocattolo(Model model) {
        Utente utente = utenteService.getUtente();
        if (utente != null) {
            model.addAttribute("credenziali", credenzialiService.getCredenziali(utente.getId()));
        }
        List<Giocattolo> giocattoli = new java.util.ArrayList<>();
        for (Giocattolo g : this.giocattoloService.getAllGiocattoli()) {
            giocattoli.add(g);
        }
        giocattoli.sort(Comparator.comparingLong(Giocattolo::getId));
        model.addAttribute("giocattoli", giocattoli);
        return "aggiornaGiocattolo";
    }

    @GetMapping("/cancellaGiocattolo/{id}")
    public String deleteGiocattolo(@PathVariable("id") Long id, Model model) {
        this.giocattoloService.deleteById(id);
        return "redirect:/aggiornaGiocattolo";
    }
}
