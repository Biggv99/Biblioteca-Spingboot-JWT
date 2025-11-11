package com.example.bankapi.dto;

public class LibroRequest {

    private Long id;
    private String titolo, autore, genere, anno;
    private int numeroCopie;

    //Metodi get//
    public Long getId() { return id; }
    public String getTitolo() { return titolo; }
    public String getAutore() { return autore; }
    public String getGenere() { return genere; }
    public String getAnno() { return anno; }
    public int getNumeroCopie(){ return numeroCopie; }

    //Metodi set//
    public void setId(Long id){ this.id = id; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public void setAutore(String autore) { this.autore = autore; }
    public void setGenere(String genere) { this.genere = genere; }
    public void setAnno(String anno) { this.anno = anno; }
    public void setNumeroCopie(int numeroCopie) { this.numeroCopie = numeroCopie; }
}
