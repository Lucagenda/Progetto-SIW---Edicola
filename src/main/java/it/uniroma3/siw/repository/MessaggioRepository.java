package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Utente;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long> {
    List<Messaggio> findByDestinatarioOrderByDataDesc(Utente destinatario);
}
