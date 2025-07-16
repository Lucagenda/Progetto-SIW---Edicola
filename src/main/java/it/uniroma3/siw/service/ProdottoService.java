package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Giocattolo;
import it.uniroma3.siw.model.Giornale;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.GiocattoloRepository;
import it.uniroma3.siw.repository.GiornaleRepository;
import it.uniroma3.siw.repository.ProdottoRepository;

@Service
public class ProdottoService {
	
	@Autowired
	private ProdottoRepository prodottoRepository;
	
	@Autowired
	private GiornaleRepository giornaleRepository;
	
	@Autowired
	private GiocattoloRepository giocattoloRepository;

	public List<Prodotto> getAllProdotti() {
		return prodottoRepository.findAll();
	}
	
	public Prodotto getProdottoById(Long id) {
		return prodottoRepository.findById(id).get();
	}
	
	public void save(Prodotto prodotto) {
		prodottoRepository.save(prodotto);
	}
	
	public void deleteById(Long id) {
		prodottoRepository.deleteById(id);
	}
	
	public List<Prodotto> cercaPerNome(String nome) {
		List<Giornale> giornali = giornaleRepository.findByNomeContainingIgnoreCase(nome);
		List<Giocattolo> giocattoli = giocattoloRepository.findByNomeContainingIgnoreCase(nome);

		List<Prodotto> risultati = new ArrayList<>();
		risultati.addAll(giornali);
		risultati.addAll(giocattoli);

		return risultati;
	}

}
