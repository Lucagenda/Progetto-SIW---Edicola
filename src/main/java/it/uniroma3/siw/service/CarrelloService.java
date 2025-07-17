// ...existing code...

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
    public void aggiungiProdottoConQuantita(Utente utente, Prodotto prodotto, int quantita) {
        Carrello carrello = getOrCreateCarrello(utente);
        Optional<VoceCarrello> voceEsistente = carrello.getVoci().stream()
            .filter(v -> v.getProdotto().getId().equals(prodotto.getId()))
            .findFirst();
        if (voceEsistente.isPresent()) {
            VoceCarrello voce = voceEsistente.get();
            voce.setQuantita(voce.getQuantita() + quantita);
            voceCarrelloRepository.save(voce);
        } else {
            VoceCarrello voce = new VoceCarrello();
            voce.setProdotto(prodotto);
            voce.setCarrello(carrello);
            voce.setQuantita(quantita);
            voceCarrelloRepository.save(voce);
            carrello.getVoci().add(voce);
        }
        carrelloRepository.save(carrello);
    }

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

    public void svuotaCarrello(Utente utente) {
        Carrello carrello = getOrCreateCarrello(utente);
        if (carrello.getVoci() != null) {
            for (VoceCarrello voce : new ArrayList<>(carrello.getVoci())) {
                voceCarrelloRepository.deleteById(voce.getId());
            }
            carrello.getVoci().clear();
            carrelloRepository.save(carrello);
        }
    }
}