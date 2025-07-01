package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Giocattolo;

public interface GiocattoloRepository extends CrudRepository<Giocattolo, Long> {

}
