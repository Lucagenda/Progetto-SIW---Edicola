package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Giornale {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	@NotBlank
	private String periodicita;
	@NotNull
	private Integer anno;
	
	private String urlImage;
	
	@OneToOne
	private Giornalista direttore;
	
	@ManyToMany
	private List<Giornalista> editorialisti;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

	public Giornalista getDirettore() {
		return direttore;
	}

	public void setDirettore(Giornalista direttore) {
		this.direttore = direttore;
	}

	public List<Giornalista> getEditorialisti() {
		return editorialisti;
	}

	public void setEditorialisti(List<Giornalista> editorialisti) {
		this.editorialisti = editorialisti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno, direttore, editorialisti, id, nome, periodicita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giornale other = (Giornale) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(direttore, other.direttore)
				&& Objects.equals(editorialisti, other.editorialisti) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(periodicita, other.periodicita);
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
}
