package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Giornale;
import it.uniroma3.siw.repository.GiornaleRepository;
import it.uniroma3.siw.repository.VoceCarrelloRepository;
import it.uniroma3.siw.model.VoceCarrello;

@Service
public class GiornaleService {
	@Autowired
	VoceCarrelloRepository voceCarrelloRepository;
	
	@Autowired
	GiornaleRepository giornaleRepository;

	public Iterable<Giornale> getAllGiornali() {
		return giornaleRepository.findAll();
	}

	public Giornale getGiornaleById(Long id) {
		return giornaleRepository.findById(id).get();
	}

	public void save(Giornale giornale) {
		giornaleRepository.save(giornale);
	}

	public void deleteById(Long id) {
		Giornale giornale = giornaleRepository.findById(id).orElse(null);
		if (giornale != null) {
			// Cancella solo le voci di carrello che referenziano il giornale
			java.util.List<VoceCarrello> vociCarrello = voceCarrelloRepository.findByProdotto(giornale);
			if (vociCarrello != null && !vociCarrello.isEmpty()) {
				voceCarrelloRepository.deleteAll(vociCarrello);
			}
			// Cancella il giornale
			giornaleRepository.deleteById(id);
		}
	}
	
	@Transactional
	public List<Giornale> cercaPerNome(String nome) {
		return giornaleRepository.findByNome(nome);
	}
}
