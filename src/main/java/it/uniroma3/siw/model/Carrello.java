package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrello {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @OneToOne
    private Utente utente;

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, orphanRemoval = true) //orphanRemoval fa si che se rimuovi una voce dal carrello, viene eliminata dal DB.
    private List<VoceCarrello> voci;

    /* metodo per il totale */
    public double getTotale() {
        double totale = 0.0;
        if (voci != null) {
            for (VoceCarrello voce : voci) {
                double prezzo = voce.getProdotto().getPrezzo();
                int quantita = voce.getQuantita();
                totale += prezzo * quantita;
            }
        }
        return totale;
    }
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public List<VoceCarrello> getVoci() {
		return voci;
	}

	public void setVoci(List<VoceCarrello> voci) {
		this.voci = voci;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, utente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carrello other = (Carrello) obj;
		return Objects.equals(id, other.id) && Objects.equals(utente, other.utente);
	}
	
}
