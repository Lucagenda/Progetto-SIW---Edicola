package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Giocattolo;

public interface GiocattoloRepository extends JpaRepository<Giocattolo, Long> {
	
	List<Giocattolo> findByNome(String nome);

	List<Giocattolo> findByNomeContainingIgnoreCase(String nome);
}
