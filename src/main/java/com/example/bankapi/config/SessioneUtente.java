package com.example.bankapi.config;

public class SessioneUtente {

    private static SessioneUtente instance;
    private String nome;
    private String ruolo;
    private String email;
    private String token;

    private SessioneUtente() {
        this.nome = null;
        this.ruolo = null;
        this.email = null;
        this.token = null;
    }

    public static SessioneUtente getInstance() {
        if (instance == null) {
            instance = new SessioneUtente();
        }
        return instance;
    }

    public void setSessione(String nome, String ruolo, String email, String token) {
        this.nome = nome;
        this.ruolo = ruolo;
        this.email = email;
        this.token = token;
    }

    public void logout() {
        this.nome = null;
        this.ruolo = null;
        this.email = null;
        this.token = null;
    }

    public String getNome() { return nome; }
    public String getRuolo() { return ruolo; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}
