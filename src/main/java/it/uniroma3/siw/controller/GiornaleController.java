package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.GiornaleService;
import it.uniroma3.siw.service.GiornalistaService;

@Controller
public class GiornaleController {
	
	@Autowired
	GiornaleService giornaleService;
	
	@Autowired
	GiornalistaService giornalistaService;
	
	@GetMapping("/giornali")
	public String mostraTuttiGiornali(Model model) {
		model.addAttribute("gioenali", this.giornaleService.getAllGiornali());
		return "giornali.html";
	}
}
