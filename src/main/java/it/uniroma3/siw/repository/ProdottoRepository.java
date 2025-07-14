package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Prodotto;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    List<Prodotto> findByNomeContainingIgnoreCase(String nome);
}
