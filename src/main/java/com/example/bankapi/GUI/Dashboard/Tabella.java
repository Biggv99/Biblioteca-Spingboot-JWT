package com.example.bankapi.GUI.Dashboard;

import com.example.bankapi.GUI.Messaggi.Conferma;
import com.example.bankapi.GUI.Messaggi.Messaggio;
import com.example.bankapi.config.SaveLoad;
import com.example.bankapi.config.SessioneUtente;
import com.example.bankapi.model.Libro;
import com.example.bankapi.model.Prestito;
import com.example.bankapi.model.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Tabella extends JPanel implements SaveLoad {

    private DefaultTableModel modelloTabella;
    private final JTable tabella;

    private ArrayList<Utente> utenti = new ArrayList<>();
    private ArrayList<Libro> libri = new ArrayList<>();
    private ArrayList<Prestito> prestiti = new ArrayList<>();
    private int listaAttuale;

    public Tabella() {
        setLayout(new BorderLayout());

        tabella = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabella);
        add(scrollPane, BorderLayout.CENTER);

        listaAttuale = 0;
        cambiaLista(listaAttuale);
        setVisible(false);
    }

    // CAMBIA LA LISTA
    public void cambiaLista(int scelta) {

        String[] colonne;
        switch (scelta) {
            case 1 -> {
                colonne = new String[]{"ID", "NOME", "EMAIL", "PASSWORD", "RUOLO"};
                listaAttuale = 1;
            }
            case 2 -> {
                colonne = new String[]{"ID", "TITOLO", "AUTORE", "GENERE", "ANNO PUBBLICAZIONE", "COPIE"};
                listaAttuale = 2;
            }
            case 3 -> {
                colonne = new String[]{"ID", "UTENTE", "LIBRO", "DATA INIZIO", "DATA FINE", "RESTITUITO"};
                listaAttuale = 3;
            }
            default -> {
                colonne = new String[]{"IN ATTESA DELLA LISTA DA VISUALIZZARE"};
                listaAttuale = 0;
            }
        }

        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        tabella.setModel(modelloTabella);
        tabella.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));

        prendiTabellaDatabase(scelta);
    }

    // PRENDE DAL DATABASE LA LISTA SELEZIONATA
    private void prendiTabellaDatabase(int scelta) {
        try {
            String endpoint;
            switch (scelta) {
                case 1 -> endpoint = "http://localhost:8080/api/auth/admin/view";
                case 2 -> endpoint = "http://localhost:8080/api/libro/view";
                case 3 -> endpoint = "http://localhost:8080/api/prestito/view";
                default -> { return; }
            }

            HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET");
            if(scelta == 1 || scelta == 3) { connection.setRequestProperty("Authorization", "Bearer " + SessioneUtente.getInstance().getToken());}
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                ObjectMapper objectMapper = new ObjectMapper();
                modelloTabella.setRowCount(0);
                utenti.clear();
                libri.clear();
                prestiti.clear();

                switch (scelta) {
                    case 1 -> {
                        Utente[] utentiArray = objectMapper.readValue(response.toString(), Utente[].class);
                        for (Utente u : utentiArray) {
                            utenti.add(u);
                            modelloTabella.addRow(new Object[]{u.getId(), u.getNome(), u.getEmail(), u.getPassword(), u.getPermessi()});
                        }
                    }
                    case 2 -> {
                        Libro[] libriArray = objectMapper.readValue(response.toString(), Libro[].class);
                        for (Libro l : libriArray) {
                            libri.add(l);
                            modelloTabella.addRow(new Object[]{
                                    l.getId(), l.getTitolo(), l.getAutore(), l.getGenere(),
                                    l.getAnno(), l.getNumeroCopie()
                            });
                        }
                    }
                    case 3 -> {
                        Prestito[] prestitiArray = objectMapper.readValue(response.toString(), Prestito[].class);
                        for (Prestito p : prestitiArray) {
                            prestiti.add(p);
                            modelloTabella.addRow(new Object[]{
                                    p.getId(), p.getUtente(), p.getLibro(),
                                    p.getDataInizio(), p.getDataFine(), p.getRestituito()
                            });
                        }
                    }
                }
            }
        } catch (Exception e) { new Messaggio("Errore: importazione tabella falita"); }
    }

    // ESPORTA LA LISTA IN UN FILE TXT
    public void salvaLista(){

        if(listaAttuale == 0){
            new Messaggio("Nessuna lista da esportare");
            return;
        }

        String path = "./src/main/java/com/example/bankapi/SaveData/";

        JFileChooser saveFile = new JFileChooser(path);
        int risposta = saveFile.showSaveDialog(null);

        if(risposta == JFileChooser.APPROVE_OPTION){

            File tmpDir = new File(path+saveFile.getSelectedFile().getName());

            if(tmpDir.exists()){ new Messaggio("Errore: il file esiste già"); }
            else {
                switch (listaAttuale) {
                    case 1 -> esportaUtenti(utenti, tmpDir.getAbsolutePath() + ".txt");
                    case 2 -> esportaLibri(libri, tmpDir.getAbsolutePath() + ".txt");
                    case 3 -> esportaPresiti(prestiti, tmpDir.getAbsolutePath() + ".txt");
                }
            }
        }
    }

    // CERCA FRA GLI UTENTI
    public void CercaUtenti(String nome, String email, String ruolo) {

        ArrayList<Utente> copia = new ArrayList<>();

        if(nome.isEmpty() && email.isEmpty() && ruolo.isEmpty()){
            new Messaggio("Devi dirmi cosa cercare");
            return;
        }

        for (Utente u : utenti) {
            boolean nomeValido = nome.isEmpty() || u.getNome().equals(nome);
            boolean emailValido = email.isEmpty() || u.getEmail().equals(email);
            boolean ruoloValido = ruolo.isEmpty() || u.getPermessi().equals(ruolo);

            if (nomeValido && emailValido && ruoloValido) {
                copia.add(u);
            }
        }

        utenti = copia;
        modelloTabella.setRowCount(0);
        for (Utente u : utenti) {
            modelloTabella.addRow(new Object[]{u.getId(), u.getNome(), u.getEmail(), u.getPassword(), u.getPermessi()});
        }
    }

    // CERCA FRA I LIBRI
    public void CercaLibri(String titolo, String autore, String genere, String numeroCopie) {

        ArrayList<Libro> copia = new ArrayList<>();

        if (titolo.isEmpty() && autore.isEmpty() && genere.isEmpty() && numeroCopie.isEmpty()) {
            new Messaggio("Inserisci almeno un criterio di ricerca");
            return;
        }

        int copie = 0;
        if (!numeroCopie.isEmpty()){
            try { copie = Integer.parseInt(numeroCopie); }
            catch (Exception e){
                new Messaggio("Numero copie invalido");
                return;
            }
        }

        for (Libro l : libri) {
            boolean titoloValido = titolo.isEmpty() || l.getTitolo().equals(titolo);
            boolean autoreValido = autore.isEmpty() || l.getAutore().equals(autore);
            boolean genereValido = genere.isEmpty() || l.getGenere().equals(genere);
            boolean copieValide = numeroCopie.isEmpty() || l.getNumeroCopie() == copie;

            if (titoloValido && autoreValido && genereValido && copieValide) {
                copia.add(l);
            }
        }

        libri = copia;
        modelloTabella.setRowCount(0);
        for (Libro l : libri) {
            modelloTabella.addRow(new Object[]{
                    l.getId(), l.getTitolo(), l.getAutore(), l.getGenere(), l.getAnno(), l.getNumeroCopie()
            });
        }
    }

    // CERCA FRA LE PRENOTAZIONI
    public void CercaPrenotazioni(String utente, String libro, String restituito){

        ArrayList<Prestito> copia = new ArrayList<>();

        if (utente.isEmpty() && libro.isEmpty() && restituito.isEmpty()) {
            new Messaggio("Inserisci almeno un criterio di ricerca");
            return;
        }

        for (Prestito p : prestiti) {
            boolean utenteValido = utente.isEmpty() || p.getUtente().equals(utente);
            boolean libroValido = libro.isEmpty() || p.getLibro().equals(libro);
            boolean restituitoValido = restituito.isEmpty() || p.getRestituito().equals(restituito);

            if (utenteValido && libroValido && restituitoValido) {
                copia.add(p);
            }
        }

        prestiti = copia;
        modelloTabella.setRowCount(0);
        for (Prestito p : prestiti) {
            modelloTabella.addRow(new Object[]{
                    p.getId(), p.getUtente(), p.getLibro(), p.getDataInizio(), p.getDataFine(), p.getRestituito()
            });
        }
    }

    // ELIMINA UNA RIGA
    public void eliminaRiga(){

        int riga = tabella.getSelectedRow();

        if (riga == -1) {
            new Messaggio("Devi selezionare il qualcosa dalla tabella per eliminare");
            return;
        }

        String soggetto = (String)modelloTabella.getValueAt(riga, 1);

        Conferma conferma = new Conferma("Vuoi eliminare: "+soggetto+"?");
        conferma.attivaBottoni(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton o = (JButton) e.getSource();
                if (o.getText().equals("si")) {

                    try {

                        String endpoint;
                        switch (listaAttuale) {
                            case 1 -> endpoint = "http://localhost:8080/api/auth/admin/delete";
                            case 2 -> endpoint = "http://localhost:8080/api/libro/admin/delete";
                            case 3 -> endpoint = "http://localhost:8080/api/prestito/admin/delete";
                            default -> { return; }
                        }

                        URL url = new URL(endpoint);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Authorization", "Bearer " + SessioneUtente.getInstance().getToken());
                        connection.setDoOutput(true);

                        String json;
                        switch (listaAttuale) {
                            case 1 -> json = "{"
                                    + "\"nome\": \"" + modelloTabella.getValueAt(riga, 1) + "\""
                                    + "}";

                            case 2, 3 -> json = "{"
                                    + "\"id\": \"" + modelloTabella.getValueAt(riga, 0) + "\""
                                    + "}";
                            default -> { return; }
                        }

                        try (OutputStream os = connection.getOutputStream()) {
                            byte[] input = json.getBytes(StandardCharsets.UTF_8);
                            os.write(input, 0, input.length);
                        }

                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {

                            modelloTabella.removeRow(riga);
                            switch (listaAttuale) {
                                case 1 -> utenti.remove(riga);
                                case 2 -> libri.remove(riga);
                                case 3 -> prestiti.remove(riga);
                                default -> { return; }
                            }

                            new Messaggio("Rimosso con successo!");
                        } else { new Messaggio("Richiesta errata"); }
                    } catch (Exception ex) { new Messaggio("Errore: eliminazione fallita"); }

                    conferma.dispose();
                }

                if (o.getText().equals("no")) { conferma.dispose(); }
            }
        });
    }

    // MODIFICA LIBRO
    public void modificaLibro() {

        int riga = tabella.getSelectedRow();
        if (riga == -1) {
            new Messaggio("Devi selezionare il libro modificato dalla tabella");
            return;
        }

        try {

            URL url = new URL("http://localhost:8080/api/libro/admin/modifica");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + SessioneUtente.getInstance().getToken());
            connection.setDoOutput(true);

            String id = modelloTabella.getValueAt(riga, 0).toString();
            String titolo = modelloTabella.getValueAt(riga, 1).toString();
            String autore = modelloTabella.getValueAt(riga, 2).toString();
            String genere = modelloTabella.getValueAt(riga, 3).toString();
            String anno = modelloTabella.getValueAt(riga, 4).toString();
            String copie = modelloTabella.getValueAt(riga, 5).toString();

            String json = "{"
                    + "\"id\": \"" + id + "\", "
                    + "\"titolo\": \"" + titolo + "\", "
                    + "\"autore\": \"" + autore + "\", "
                    + "\"genere\": \"" + genere + "\", "
                    + "\"anno\": \"" + anno + "\", "
                    + "\"numeroCopie\": \"" + copie + "\""
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                new Messaggio("Modifiche apportate!");
            } else {
                new Messaggio("Richiesta modifica errata");
            }
        } catch (Exception ex) { new Messaggio("Errore: modifica fallita"); }
    }

    // RITORNA LA LISTA DEI PRESTITI
    public ArrayList<Prestito> getPrestito() {

        ArrayList<Prestito> copia = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:8080/api/prestito/view");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                ObjectMapper objectMapper = new ObjectMapper();
                Prestito[] prestitiArray = objectMapper.readValue(response.toString(), Prestito[].class);

                copia.addAll(Arrays.asList(prestitiArray));

            } else { new Messaggio("Richiesta visualizza prestiti fallita"); }

        } catch (Exception e) { new Messaggio("Errore connessione durante la visualizzazione dei prestiti"); }

        return copia;
    }

    // EFFETTUA PRENOTAZIONE
    public void prenota() {

        int riga = tabella.getSelectedRow();
        if (riga == -1) {
            new Messaggio("Devi selezionare un libro per prenotarlo");
            return;
        }

        String copie = modelloTabella.getValueAt(riga, 5).toString();
        if (copie.equals("0")) {
            new Messaggio("Non ci sono copie disponibili per questo libro");
            return;
        }

        try {

            URL url = new URL("http://localhost:8080/api/prestito/aggiungi");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + SessioneUtente.getInstance().getToken());
            connection.setDoOutput(true);

            LocalDate oggi = LocalDate.now();
            LocalDate fine = oggi.plusMonths(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String utente = SessioneUtente.getInstance().getNome();
            String libro = modelloTabella.getValueAt(riga, 1).toString();
            String dataInizio = oggi.format(formatter);
            String dataFine = fine.format(formatter);
            String restituito = "No";

            String json = "{"
                    + "\"utente\": \"" + utente + "\", "
                    + "\"libro\": \"" + libro + "\", "
                    + "\"dataInizio\": \"" + dataInizio + "\", "
                    + "\"dataFine\": \"" + dataFine + "\", "
                    + "\"restituito\": \"" + restituito + "\""
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                URL url2 = new URL("http://localhost:8080/api/libro/copie");
                HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                connection2.setRequestMethod("PUT");
                connection2.setRequestProperty("Content-Type", "application/json");
                connection2.setDoOutput(true);

                String id = modelloTabella.getValueAt(riga, 0).toString();

                String json2 = "{"
                        + "\"id\": \"" + id + "\", "
                        + "\"numeroCopie\": \"" + -1 + "\""
                        + "}";

                try (OutputStream os2 = connection2.getOutputStream()) {
                    byte[] input2 = json2.getBytes(StandardCharsets.UTF_8);
                    os2.write(input2, 0, input2.length);
                }

                int responseCode2 = connection2.getResponseCode();
                if (responseCode2 != HttpURLConnection.HTTP_OK) {
                    new Messaggio("Errore durante l'aggiornamento del numero copie!");
                    return;
                }

                cambiaLista(2);
                new Messaggio("Libro prenotato con successo!");

            } else { new Messaggio("Richiesta prenotazione errata"); }
        } catch (Exception ex) { new Messaggio("Errore: prenotazione fallita"); }
    }

    // RESTITUISCE LA PRENOTAZIONE
    public void restituisci(String id, String libro, String stato, int n){

        try {

            URL url = new URL("http://localhost:8080/api/prestito/modifica");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String json = "{"
                    + "\"id\": \"" + id + "\", "
                    + "\"restituito\": \"" + stato + "\""
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                URL url2 = new URL("http://localhost:8080/api/libro/copie");
                HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                connection2.setRequestMethod("PUT");
                connection2.setRequestProperty("Content-Type", "application/json");
                connection2.setDoOutput(true);

                String json2 = "{"
                        + "\"id\": \"" + -1 + "\", "
                        + "\"titolo\": \"" + libro + "\", "
                        + "\"numeroCopie\": \"" + n + "\""
                        + "}";

                try (OutputStream os2 = connection2.getOutputStream()) {
                    byte[] input2 = json2.getBytes(StandardCharsets.UTF_8);
                    os2.write(input2, 0, input2.length);
                }

                int responseCode2 = connection2.getResponseCode();
                if (responseCode2 != HttpURLConnection.HTTP_OK) {
                    new Messaggio("Errore durante l'aggiornamento del numero copie!");
                    return;
                }

                cambiaLista(2);
                if(stato.equals("si")){
                    new Messaggio("Libro restituito con successo!");
                } else {
                    new Messaggio("Operazione annullata");
                }

            } else { new Messaggio("Richiesta restituzione errata"); }
        } catch (Exception ex) { new Messaggio("Errore di connessione durante la restituzione"); }
    }
}