package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;

public interface CarrelloRepository extends JpaRepository<Carrello, Long> {

	Optional<Carrello> findByUtente(Utente utente);

}
