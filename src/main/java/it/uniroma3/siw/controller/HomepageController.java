package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

	@GetMapping("/")
	public String home(){
		return "index.html";
	}
	
	@GetMapping("/chi-siamo")
	public String chiSiamo() {
	    return "chi-siamo";
	}
}
