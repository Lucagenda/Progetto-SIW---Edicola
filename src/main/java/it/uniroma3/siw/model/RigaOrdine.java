package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RigaOrdine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @ManyToOne
    private Ordine ordine;

    @ManyToOne
    private Prodotto prodotto;

    private int quantita;

	public Long getId() {
		return id;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}



	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, ordine, quantita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RigaOrdine other = (RigaOrdine) obj;
		return Objects.equals(id, other.id) && Objects.equals(ordine, other.ordine) && quantita == other.quantita;
	}
}

