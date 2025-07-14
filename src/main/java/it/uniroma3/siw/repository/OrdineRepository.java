package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Ordine;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {

}
