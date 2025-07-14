package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Credenziali;

public interface CredenzialiRepository extends JpaRepository<Credenziali, Long> {

	public Optional<Credenziali> findByUsername(String username);
}
