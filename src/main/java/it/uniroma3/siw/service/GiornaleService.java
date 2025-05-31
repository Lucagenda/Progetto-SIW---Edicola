package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Giornale;
import it.uniroma3.siw.repository.GiornaleRepository;

@Service
public class GiornaleService {
	
	@Autowired
	GiornaleRepository giornaleRepository;

	public Iterable<Giornale> getAllGiornali() {
		return giornaleRepository.findAll();
	}
}
