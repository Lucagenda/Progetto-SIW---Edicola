package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CredenzialiRepository credenzialiRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credenziali credenziali = credenzialiRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));

        return new org.springframework.security.core.userdetails.User(
            credenziali.getUsername(),
            credenziali.getPassword(),
            List.of(new SimpleGrantedAuthority(credenziali.getRuolo()))
        );
    }
}
