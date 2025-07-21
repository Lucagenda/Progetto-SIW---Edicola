package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Giocattolo;
import it.uniroma3.siw.repository.GiocattoloRepository;
import it.uniroma3.siw.repository.RigaOrdineRepository;
import it.uniroma3.siw.repository.VoceCarrelloRepository;
import it.uniroma3.siw.model.RigaOrdine;
import it.uniroma3.siw.model.VoceCarrello;

@Service
public class GiocattoloService {
	@Autowired
	RigaOrdineRepository rigaOrdineRepository;
	@Autowired
	VoceCarrelloRepository voceCarrelloRepository;

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
		Giocattolo giocattolo = giocattoloRepository.findById(id).orElse(null);
		if (giocattolo != null) {
			// Cancella solo le voci di carrello che referenziano il giocattolo
			java.util.List<VoceCarrello> vociCarrello = voceCarrelloRepository.findByProdotto(giocattolo);
			if (vociCarrello != null && !vociCarrello.isEmpty()) {
				voceCarrelloRepository.deleteAll(vociCarrello);
			}
			// Cancella il giocattolo
			giocattoloRepository.deleteById(id);
		}
	}
	
	@Transactional
	public List<Giocattolo> cercaPerNome(String nome) {
		return giocattoloRepository.findByNome(nome);
	}
}
