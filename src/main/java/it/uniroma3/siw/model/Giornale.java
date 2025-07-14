package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Giornale extends Prodotto {

    @NotBlank
    private String periodicita;

    @NotNull
    private Integer anno;

    private String direttore;

	public String getPeriodicita() {
		return periodicita;
	}

	public void setPeriodicita(String periodicita) {
		this.periodicita = periodicita;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public String getDirettore() {
		return direttore;
	}

	public void setDirettore(String direttore) {
		this.direttore = direttore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(anno, direttore, periodicita);
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
		Giornale other = (Giornale) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(direttore, other.direttore)
				&& Objects.equals(periodicita, other.periodicita);
	}
   
}