package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.VoceCarrello;

import it.uniroma3.siw.model.Prodotto;

public interface VoceCarrelloRepository extends JpaRepository<VoceCarrello, Long>{
    java.util.List<VoceCarrello> findByProdotto(Prodotto prodotto);
}
