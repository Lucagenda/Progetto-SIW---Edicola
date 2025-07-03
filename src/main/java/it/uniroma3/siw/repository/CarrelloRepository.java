package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;

public interface CarrelloRepository extends CrudRepository<Carrello, Long> {

	Optional<Carrello> findByUtente(Utente utente);

}
