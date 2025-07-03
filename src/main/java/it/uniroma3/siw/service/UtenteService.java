package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;


@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private CredenzialiService credenzialiService;  // Serve per recuperare le credenziali con username

	//ritorna un Utente con Id
	public Utente getUtente(Long id) {
		Optional<Utente> result = this.utenteRepository.findById(id);
		return result.orElse(null);
	}

	//metodo per ottenere l'utente loggato
	public Utente getUtente() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		Credenziali credenziali = credenzialiService.getCredenziali(username);
		return credenziali != null ? credenziali.getUtente() : null;
	}


	@Transactional
	public Utente saveUtente(Utente utente) {
		return utenteRepository.save(utente);
	}

	@Transactional
	public List<Utente> getAllUtenti() {
		List<Utente> result = new ArrayList<>();
		Iterable<Utente> iterable = this.utenteRepository.findAll();
		for(Utente utente : iterable)
			result.add(utente);
		return result;
	}

}
