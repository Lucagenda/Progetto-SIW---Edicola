package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Giocattolo;
import it.uniroma3.siw.repository.GiocattoloRepository;

@Service
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
	
	@Transactional
	public List<Giocattolo> cercaPerNome(String nome) {
	    return giocattoloRepository.findByNome(nome);
	}
}
