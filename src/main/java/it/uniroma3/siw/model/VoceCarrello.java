package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class VoceCarrello {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @ManyToOne
    private Carrello carrello;

    //@ManyToOne
    //private Articolo articolo; //ragioniamo su questo: conviene mettere una classe per ogni articolo o una calsse che generalizza gli articoli??

    private int quantita;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Carrello getCarrello() {
		return carrello;
	}

	public void setCarrello(Carrello carrello) {
		this.carrello = carrello;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(carrello, id, quantita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VoceCarrello other = (VoceCarrello) obj;
		return Objects.equals(carrello, other.carrello) && Objects.equals(id, other.id) && quantita == other.quantita;
	}
    
}
