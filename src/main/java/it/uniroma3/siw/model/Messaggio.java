package it.uniroma3.siw.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String testo;
    private LocalDateTime data;
    private boolean letto;

    @ManyToOne
    private Utente destinatario;

    // Costruttori
    public Messaggio() {}
    public Messaggio(String testo, Utente destinatario) {
        this.testo = testo;
        this.destinatario = destinatario;
        this.data = LocalDateTime.now();
        this.letto = false;
    }

    // Getter e Setter
    public Long getId() { return id; }
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public boolean isLetto() { return letto; }
    public void setLetto(boolean letto) { this.letto = letto; }
    public Utente getDestinatario() { return destinatario; }
    public void setDestinatario(Utente destinatario) { this.destinatario = destinatario; }
}
