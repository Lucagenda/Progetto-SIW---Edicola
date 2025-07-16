package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Giocattolo;
import it.uniroma3.siw.service.GiocattoloService;
import jakarta.validation.Valid;

@Controller
public class GiocattoloController {

    @Autowired
    GiocattoloService giocattoloService;

    @GetMapping("/giocattoli")
    public String mostraTuttiGiocattoli(Model model) {
        model.addAttribute("giocattoli", this.giocattoloService.getAllGiocattoli());
        return "giocattoli.html";
    }

    @GetMapping("/giocattolo/{id}")
    public String getGiocattolo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("giocattolo", this.giocattoloService.getGiocattoloById(id));    
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
        model.addAttribute("giocattoli", this.giocattoloService.getAllGiocattoli());
        return "aggiornaGiocattolo";
    }
    
    @GetMapping("modificaGiocattolo/{id}")
	public String modificaGiocattolo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("giocattolo", this.giocattoloService.getGiocattoloById(id));
		return "modificaGiocattolo.html";
	}

    @GetMapping("/cancellaGiocattolo/{id}")
    public String deleteGiocattolo(@PathVariable("id") Long id, Model model) {
        this.giocattoloService.deleteById(id);
        return "redirect:/aggiornaGiocattolo";
    }
}
