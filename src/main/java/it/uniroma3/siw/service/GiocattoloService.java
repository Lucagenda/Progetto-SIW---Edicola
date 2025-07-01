package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Giocattolo;
import it.uniroma3.siw.repository.GiocattoloRepository;

public class GiocattoloService {

	@Autowired
	GiocattoloRepository giocattoloRepository;

	public Iterable<Giocattolo> getAllGiocattoli() {
		return giocattoloRepository.findAll();
	}

	public Giocattolo getGiocattoloById(Long id) {
		return giocattoloRepository.findById(id).get();
	}

	public void save(Giocattolo giocattolo) {
		giocattoloRepository.save(giocattolo);
	}

	public void deleteById(Long id) {
		giocattoloRepository.deleteById(id);
	}
}
