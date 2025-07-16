package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Giornale;

public interface GiornaleRepository extends JpaRepository<Giornale, Long> {

	List<Giornale> findByNome(String nome);

	List<Giornale> findByNomeContainingIgnoreCase(String nome);
}
