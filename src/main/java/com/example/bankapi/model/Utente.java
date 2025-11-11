package com.example.bankapi.model;

import jakarta.persistence.*;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome, password, email, permessi;

    public Utente(String nome, String password, String email, String permessi) {
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.permessi = permessi;
    }
    public Utente() { }

    //Metodi get//
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPermessi() { return permessi; }

    //Metodi set//
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPermessi(String permessi) { this.permessi = permessi; }
}



