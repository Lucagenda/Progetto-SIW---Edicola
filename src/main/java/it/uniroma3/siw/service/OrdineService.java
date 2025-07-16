package it.uniroma3.siw.service;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.repository.OrdineRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.constants.StatoOrdine;
import it.uniroma3.siw.repository.OrdineRepository;
import java.util.List;

@Service
public class OrdineService {
    @Autowired
    private OrdineRepository ordineRepository;

    public void salvaOrdine(Ordine ordine) {
        ordineRepository.save(ordine);
    }

    public List<Ordine> getAllOrdini() {
        List<Ordine> inPreparazione = ordineRepository.findByStato(StatoOrdine.IN_PREPARAZIONE);
        List<Ordine> pronti = ordineRepository.findByStato(StatoOrdine.PRONTO);
        List<Ordine> tutti = new java.util.ArrayList<>();
        tutti.addAll(inPreparazione);
        tutti.addAll(pronti);
        return tutti;
    }

    public Ordine getOrdineById(Long id) {
        return ordineRepository.findById(id).orElse(null);
    }

    public void aggiornaStatoOrdine(Long id, StatoOrdine nuovoStato) {
        Ordine ordine = getOrdineById(id);
        if (ordine != null) {
            ordine.setStato(nuovoStato);
            ordineRepository.save(ordine);
        }
    }
}
