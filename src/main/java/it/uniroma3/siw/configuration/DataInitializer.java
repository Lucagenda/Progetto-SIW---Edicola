package it.uniroma3.siw.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;

import org.springframework.beans.factory.annotation.Autowired;

//classe che crea gli utenti con ruolo admin a runtime
@Configuration
public class DataInitializer {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CredenzialiService credenzialiService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {
            // Controlla se esiste già un admin per username "admin"
            if (credenzialiService.getCredenziali("admin") == null) {

                Utente adminUtente = new Utente();
                adminUtente.setNome("Flavio");
                adminUtente.setCognome("Evangelisti");
                adminUtente.setEmail("evangelistiflavio3@gmail.com");
                utenteService.saveUtente(adminUtente);

                Credenziali adminCredenziali = new Credenziali();
                adminCredenziali.setUsername("admin");
                adminCredenziali.setPassword(passwordEncoder.encode("d"));
                adminCredenziali.setRuolo(Credenziali.ADMIN_ROLE);  // "ROLE_ADMIN"
                adminCredenziali.setUtente(adminUtente);
                credenzialiService.saveCredenziali(adminCredenziali);

                System.out.println("Admin creato all'avvio.");
            }
        };
    }
}
