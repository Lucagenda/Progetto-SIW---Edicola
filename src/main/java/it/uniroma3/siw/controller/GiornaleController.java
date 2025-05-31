package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Giornale;
import it.uniroma3.siw.service.GiornaleService;
import it.uniroma3.siw.service.GiornalistaService;
import jakarta.validation.Valid;

@Controller
public class GiornaleController {

	@Autowired
	GiornaleService giornaleService;

	@Autowired
	GiornalistaService giornalistaService;

	@GetMapping("/giornali")
	public String mostraTuttiGiornali(Model model) {
		model.addAttribute("giornali", this.giornaleService.getAllGiornali());
		return "giornali.html";
	}

	@GetMapping("/giornale/{id}")
	public String getGiornale(@PathVariable("id") Long id, Model model) {
		model.addAttribute("giornale", this.giornaleService.getGiornaleById(id));    
		return "giornale.html";
	}

	@GetMapping("/formNewGiornale")
	public String formNewGiornale(Model model) {
		model.addAttribute("giornale", new Giornale());
		return "formNewGiornale.html";
	}

	@PostMapping("/giornale")
	public String newGiornale(@Valid @ModelAttribute("movie") Giornale giornale,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {         //Sono emersi errori nel binding
			model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
			return "formNewGiornale.html";
		} else {
			this.giornaleService.save(giornale);
			model.addAttribute("giornale", giornale);
			return "redirect:/giornale/"+giornale.getId();
		}
	}

	@GetMapping("/aggiornaGiornale")
	public String homeAggiornaGiornale(Model model) {
		model.addAttribute("giornali",this.giornaleService.getAllGiornali());
		return "aggiornaGiornale";
	}

	@GetMapping("/cancellaGiornale/{id}")
	public String deleteGiornale(@PathVariable("id") Long id, Model model) {
		this.giornaleService.deleteById(id);
		return "redirect:/aggiornaGiornale";
	}

	@GetMapping("modificaGiornale/{id}")
	public String modificaGiornale(@PathVariable("id") Long id, Model model) {
		model.addAttribute("giornale", this.giornaleService.getGiornaleById(id));
		return "modificaGiornale.html";
	}
}
