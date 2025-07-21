package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.RigaOrdine;

import it.uniroma3.siw.model.Prodotto;

public interface RigaOrdineRepository extends JpaRepository<RigaOrdine, Long> {
    java.util.List<RigaOrdine> findByProdotto(Prodotto prodotto);
}
