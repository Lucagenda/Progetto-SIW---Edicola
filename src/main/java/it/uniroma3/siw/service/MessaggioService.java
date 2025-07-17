package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.MessaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MessaggioService {
    @Autowired
    private MessaggioRepository messaggioRepository;

    @Transactional
    public Messaggio inviaMessaggio(String testo, Utente destinatario) {
        Messaggio msg = new Messaggio(testo, destinatario);
        return messaggioRepository.save(msg);
    }

    public List<Messaggio> getMessaggiPerUtente(Utente utente) {
        return messaggioRepository.findByDestinatarioOrderByDataDesc(utente);
    }

    @Transactional
    public void segnaComeLetto(Long id) {
        Messaggio msg = messaggioRepository.findById(id).orElse(null);
        if (msg != null) {
            msg.setLetto(true);
            messaggioRepository.save(msg);
        }
    }
}
