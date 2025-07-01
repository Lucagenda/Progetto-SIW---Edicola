package it.uniroma3.siw.service;

import java.util.ArrayList;
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

        VoceCarrello voce = new VoceCarrello();
        voce.setProdotto(prodotto);
        voce.setCarrello(carrello);
        voceCarrelloRepository.save(voce);

        carrello.getVoci().add(voce);
        carrelloRepository.save(carrello);
    }

    public void rimuoviVoce(Long voceId) {
        voceCarrelloRepository.deleteById(voceId);
    }
}