package com.example.bankapi.model;

import jakarta.persistence.*;

@Entity
public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String utente, libro, dataInizio, dataFine, restituito;

    public Prestito(String utente, String libro, String dataInizio, String dataFine, String restituito) {
        this.utente = utente;
        this.libro = libro;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.restituito = restituito;
    }
    public Prestito() { }

    //Metodi get//
    public Long getId() { return id; }
    public String getUtente() { return utente; }
    public String getLibro() { return libro; }
    public String getDataInizio() { return dataInizio; }
    public String getDataFine() { return dataFine; }
    public String getRestituito() { return restituito; }

    //Metodi set//
    public void setId(Long id) { this.id = id; }
    public void setUtente(String utente) { this.utente = utente; }
    public void setLibro(String libro) { this.libro = libro; }
    public void setDataInizio(String dataInizio) { this.dataInizio = dataInizio; }
    public void setDataFine(String dataFine) { this.dataFine = dataFine; }
    public void setRestituito(String restituito) { this.restituito = restituito; }
}
