package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.constants.StatoOrdine;
import it.uniroma3.siw.service.OrdineService;

import java.util.Arrays;

@Controller
public class AdminController {
    @Autowired
    private OrdineService ordineService;

    @GetMapping("/admin/ordini")
    public String listaOrdini(Model model) {
        model.addAttribute("ordini", ordineService.getAllOrdini());
        model.addAttribute("stati", Arrays.asList(StatoOrdine.values()));
        return "admin/ordiniAdmin";
    }

    @PostMapping("/admin/ordini/{id}/stato")
    public String aggiornaStatoOrdine(@PathVariable("id") Long id, @RequestParam("stato") StatoOrdine stato) {
        ordineService.aggiornaStatoOrdine(id, stato);
        return "redirect:/admin/ordini";
    }

}
