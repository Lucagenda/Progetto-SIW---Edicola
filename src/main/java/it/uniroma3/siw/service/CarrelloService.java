package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.VoceCarrello;
import it.uniroma3.siw.repository.CarrelloRepository;
import it.uniroma3.siw.repository.VoceCarrelloRepository;

@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private VoceCarrelloRepository voceCarrelloRepository;

    public Carrello getOrCreateCarrello(Utente utente) {
        return carrelloRepository.findByUtente(utente)
                .orElseGet(() -> {
                    Carrello nuovo = new Carrello();
                    nuovo.setUtente(utente);
                    nuovo.setVoci(new ArrayList<>());
                    return carrelloRepository.save(nuovo);
                });
    }

    public void aggiungiProdotto(Utente utente, Prodotto prodotto) {
        Carrello carrello = getOrCreateCarrello(utente);

        // Cerca una voce già presente per quel prodotto
        Optional<VoceCarrello> voceEsistente = carrello.getVoci().stream()
            .filter(v -> v.getProdotto().getId().equals(prodotto.getId()))
            .findFirst();

        if (voceEsistente.isPresent()) {
            // Se la voce esiste, aggiorna quantità e totale
            VoceCarrello voce = voceEsistente.get();
            voce.setQuantita(voce.getQuantita() + 1); // aggiunge 1 alla quantità

            voceCarrelloRepository.save(voce);
        } else {
            // Se la voce non esiste, crea una nuova
            VoceCarrello voce = new VoceCarrello();
            voce.setProdotto(prodotto);
            voce.setCarrello(carrello);
            voce.setQuantita(1);

            voceCarrelloRepository.save(voce);
            carrello.getVoci().add(voce);
        }

        carrelloRepository.save(carrello);
    }


    public void rimuoviVoce(Long voceId) {
        voceCarrelloRepository.deleteById(voceId);
    }
}