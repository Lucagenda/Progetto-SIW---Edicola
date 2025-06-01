package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import it.uniroma3.siw.constants.StatoOrdine;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Utente utente;

	private LocalDateTime dataOrdine;

	@Enumerated(EnumType.STRING)
	private StatoOrdine stato;

	@OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
	private List<RigaOrdine> righeOrdine;

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

	public LocalDateTime getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(LocalDateTime dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public StatoOrdine getStato() {
		return stato;
	}

	public void setStato(StatoOrdine stato) {
		this.stato = stato;
	}

	public List<RigaOrdine> getRigheOrdine() {
		return righeOrdine;
	}

	public void setRigheOrdine(List<RigaOrdine> righeOrdine) {
		this.righeOrdine = righeOrdine;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataOrdine, id, righeOrdine, stato, utente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ordine other = (Ordine) obj;
		return Objects.equals(dataOrdine, other.dataOrdine) && Objects.equals(id, other.id)
				&& Objects.equals(righeOrdine, other.righeOrdine) && stato == other.stato
				&& Objects.equals(utente, other.utente);
	}
}

