package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Giocattolo extends Prodotto {

	@NotBlank
    private String tipologia;

    @NotBlank
    private String produttore;

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getProduttore() {
		return produttore;
	}

	public void setProduttore(String produttore) {
		this.produttore = produttore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(produttore, tipologia);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocattolo other = (Giocattolo) obj;
		return Objects.equals(produttore, other.produttore) && Objects.equals(tipologia, other.tipologia);
	}
	
}
