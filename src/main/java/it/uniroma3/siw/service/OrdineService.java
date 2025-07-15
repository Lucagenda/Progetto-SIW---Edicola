package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.repository.OrdineRepository;

import org.springframework.stereotype.Service;

@Service
public class OrdineService {
    @Autowired
    private OrdineRepository ordineRepository;

    public void salvaOrdine(Ordine ordine) {
        ordineRepository.save(ordine);
    }
}
