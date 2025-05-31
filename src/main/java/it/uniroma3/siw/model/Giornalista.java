package it.uniroma3.siw.model;

import java.time.LocalDate;
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
public class Giornalista {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;
	@NotBlank
	private String cogname;
	@NotNull
	private LocalDate data;

	@OneToOne(mappedBy = "direttore")
	private Giornale giornale;
	
	@ManyToMany(mappedBy = "editorialisti")
	private List<Giornale> giornali;

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

	public String getCogname() {
		return cogname;
	}

	public void setCogname(String cogname) {
		this.cogname = cogname;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Giornale getGiornale() {
		return giornale;
	}

	public void setGiornale(Giornale giornale) {
		this.giornale = giornale;
	}

	public List<Giornale> getGiornali() {
		return giornali;
	}

	public void setGiornali(List<Giornale> giornali) {
		this.giornali = giornali;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cogname, data, giornale, giornali, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giornalista other = (Giornalista) obj;
		return Objects.equals(cogname, other.cogname) && Objects.equals(data, other.data)
				&& Objects.equals(giornale, other.giornale) && Objects.equals(giornali, other.giornali)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}
}
