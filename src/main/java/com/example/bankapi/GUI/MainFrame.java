package com.example.bankapi.GUI;

import com.example.bankapi.GUI.Account.*;
import com.example.bankapi.GUI.Dashboard.*;
import com.example.bankapi.GUI.Dashboard.Istruzioni.MenuIstruzioni;
import com.example.bankapi.GUI.Dashboard.Libro.MenuLibro;
import com.example.bankapi.GUI.Dashboard.Prestito.MenuPrestito;
import com.example.bankapi.GUI.Dashboard.Prestito.Reportistic;
import com.example.bankapi.GUI.Dashboard.Prestito.Restituisci;
import com.example.bankapi.GUI.Dashboard.Utente.MenuUtente;
import com.example.bankapi.GUI.Dashboard.Libro.AggiungiLibro;
import com.example.bankapi.GUI.Dashboard.Libro.CercaLibro;
import com.example.bankapi.config.SaveLoad;
import com.example.bankapi.config.SessioneUtente;
import com.example.bankapi.model.Prestito;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame implements SaveLoad {

    private final Home home;
    private final Login login;
    private final Register register;

    private final Opzioni opzioni;
    private final Tabella tabella;

    private final MenuIstruzioni menuIstruzioni;
    private final MenuUtente menuUtente;
    private final MenuLibro menuLibro;
    private final MenuPrestito menuPrestito;

    public MainFrame() {

        //Impostazioni JFrame//
        super("Libreria ITS");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Impostazioni Pannelli//
        home = new Home(); home.setBounds(320, 180, 640, 360);
        login = new Login(); login.setBounds(320, 180, 640, 360);
        register = new Register(); register.setBounds(320, 180, 640, 360);

        opzioni = new Opzioni(); opzioni.setBounds(0, 0, 1280, 30);
        tabella = new Tabella(); tabella.setBounds(300, 30, 980, 690);

        menuIstruzioni = new MenuIstruzioni(); menuIstruzioni.setBounds(0, 30, 300, 690);
        menuUtente = new MenuUtente(); menuUtente.setBounds(0, 30, 300, 690);
        menuLibro = new MenuLibro(); menuLibro.setBounds(0, 30, 300, 690);
        menuPrestito = new MenuPrestito(); menuPrestito.setBounds(0, 30, 300, 690);


        add(home);
        add(login);
        add(register);

        add(opzioni);
        add(tabella);

        add(menuIstruzioni);
        add(menuUtente);
        add(menuLibro);
        add(menuPrestito);

        //Impostazioni finali//
        configuraBottoni();
        setVisible(true);
    }

    private void configuraBottoni() {

        //Bottoni Home//
        home.attivaBottoni(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JButton source = (JButton) e.getSource();
                if (source == home.getRegister()) {
                    home.setVisible(false);
                    register.setVisible(true);
                }
                if (source == home.getLogin()) {
                    home.setVisible(false);
                    login.setVisible(true);
                }
            }
        });

        //Bottoni Login//
        login.attivaBottoni(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JButton source = (JButton) e.getSource();
                if (source == login.getAccedi()) {
                    if(login.Accesso()){

                        login.setVisible(false);

                        //imposto la visualizzazione dell'account//
                        opzioni.setAccount();

                        tabella.setVisible(true);
                        menuIstruzioni.setVisible(true);
                        opzioni.setVisible(true); opzioni.checkRuolo();
                    }
                }
                if (source == login.getIndietro()) {
                    login.setVisible(false);
                    home.setVisible(true);
                }
            }
        });

        //Bottoni Register//
        register.attivaBottoni(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JButton source = (JButton) e.getSource();
                if (source == register.getRegistrati()) {
                    register.Registrazione();
                }
                if (source == register.getIndietro()) {
                    register.setVisible(false);
                    home.setVisible(true);
                }
            }
        });

        //Bottoni opzioni//
        opzioni.attivaBottoni(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                JMenuItem source = (JMenuItem) e.getSource();
                if (source == opzioni.getEsci()){

                    tabella.setVisible(false);
                    menuIstruzioni.setVisible(false);
                    menuUtente.setVisible(false);
                    menuLibro.setVisible(false);
                    menuPrestito.setVisible(false);
                    opzioni.setVisible(false);

                    //faccio il logout//
                    SessioneUtente.getInstance().logout();

                    home.setVisible(true);
                }
                if (source == opzioni.getEsporta()){ tabella.salvaLista(); }
                if (source == opzioni.getUtenti()){
                    tabella.cambiaLista(1);
                    menuIstruzioni.setVisible(false);
                    menuUtente.setVisible(true);
                    menuLibro.setVisible(false);
                    menuPrestito.setVisible(false);
                }
                if (source == opzioni.getLibri()){
                    tabella.cambiaLista(2);
                    menuIstruzioni.setVisible(false);
                    menuUtente.setVisible(false);
                    menuLibro.setVisible(true); menuLibro.checkPremessi();
                    menuPrestito.setVisible(false);
                }
                if (source == opzioni.getPrestiti()){
                    tabella.cambiaLista(3);
                    menuIstruzioni.setVisible(false);
                    menuUtente.setVisible(false);
                    menuLibro.setVisible(false);
                    menuPrestito.setVisible(true);
                }
                if (source == opzioni.getIstruzioni()){
                    tabella.cambiaLista(0);
                    menuIstruzioni.setVisible(true);
                    menuUtente.setVisible(false);
                    menuLibro.setVisible(false);
                    menuPrestito.setVisible(false);
                }
            }
        });

        //Bottoni Menu Utente//
        menuUtente.attivaBottoni(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JButton source = (JButton) e.getSource();
                if (source == menuUtente.getElimina()) { tabella.eliminaRiga(); }
                if (source == menuUtente.getCerca()) {
                    String nome = menuUtente.getNome();
                    String email = menuUtente.getEmail();
                    String ruolo = menuUtente.getRuolo();
                    tabella.CercaUtenti(nome, email, ruolo);
                }
            }
        });

        //Bottoni Menu Libro//
        menuLibro.attivaBottoni(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JButton source = (JButton) e.getSource();

                //ADMIN//
                if (source == menuLibro.getAdminAggiungi()) {
                    AggiungiLibro aggiungi = new  AggiungiLibro();
                    aggiungi.attivaBottoni(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JButton o = (JButton) e.getSource();
                            if (o.getText().equals("Aggiungi libro")) {
                                aggiungi.aggiungiLibro();
                                tabella.cambiaLista(2);
                            }
                            if (o.getText().equals("Esci")) {
                                aggiungi.dispose();
                            }
                        }
                    });

                }
                if (source == menuLibro.getAdminRimuovi()) { tabella.eliminaRiga(); }
                if (source == menuLibro.getAdminModifica()) { tabella.modificaLibro(); }

                //UTENTE//
                if (source == menuLibro.getUtentePrenota()) { tabella.prenota(); }
                if (source == menuLibro.getUtenteRestituisci()) {
                    ArrayList<Prestito> lista = tabella.getPrestito();
                    new Restituisci(lista, tabella);
                }
                if (source == menuLibro.getUtenteCerca()) {
                    CercaLibro cerca = new CercaLibro();
                    cerca.attivaBottoni(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JButton o = (JButton) e.getSource();
                            if (o.getText().equals("Cerca libro")) {
                                tabella.CercaLibri(cerca.getTitolo(), cerca.getAutore(), cerca.getGenere(), cerca.getNumeroCopie());
                            }
                            if (o.getText().equals("Esci")) {
                                cerca.dispose();
                            }
                        }
                    });
                }
            }
        });

        //Bottoni Menu Prestito//
        menuPrestito.attivaBottoni(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JButton source = (JButton) e.getSource();
                if (source == menuPrestito.getCerca()) {
                    String utente = menuPrestito.getUtente();
                    String libro = menuPrestito.getLibro();
                    String restituito = menuPrestito.getRestituito();
                    tabella.CercaPrenotazioni(utente, libro, restituito);
                }
                if (source == menuPrestito.getReport()){
                    ArrayList<Prestito> lista = tabella.getPrestito();
                    new Reportistic(lista);
                }
            }
        });
    }
}
