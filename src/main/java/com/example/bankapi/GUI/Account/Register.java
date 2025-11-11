package com.example.bankapi.GUI.Account;

import com.example.bankapi.GUI.Dashboard.Style;
import com.example.bankapi.GUI.Messaggi.Messaggio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Register extends JPanel implements Style {

    private final JButton registrati, indietro;
    private final JTextField username, email, amministratore;
    private final JPasswordField password, confermaPassword;
    private final JPanel centro;

    public Register() {

        // Impostazione Layout
        setLayout(new BorderLayout());

        // Creazione Pannelli
        JPanel nord = new JPanel(new FlowLayout());
        nord.setBackground(Color.BLACK);

        centro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centro.setBackground(Color.DARK_GRAY);

        JPanel sud = new JPanel(new FlowLayout());
        sud.setBackground(Color.BLACK);

        // Pannello Nord - Titolo
        JLabel titolo = createStyledLabel("REGISTRAZIONE");
        titolo.setFont(new Font("Monospaced", Font.PLAIN, 48));
        nord.add(titolo);

        // Pannelo Centro - Componenti
        username = createTextField();
        email = createTextField();
        password = new JPasswordField();
        confermaPassword = new JPasswordField();
        amministratore = createTextField();

        // Riga 1 - Username
        addComponent(createStyledLabel("Username:"), gbc, 0, 0);
        addComponent(username, gbc, 1, 0);

        // Riga 2 - Email
        addComponent(createStyledLabel("Email:"), gbc, 0, 1);
        addComponent(email, gbc, 1, 1);

        // Riga 3 - Password
        addComponent(createStyledLabel("Password:"), gbc, 0, 2);
        addComponent(password, gbc, 1, 2);

        // Riga 4 - Conferma password
        addComponent(createStyledLabel("Conferma password:"), gbc, 0, 3);
        addComponent(confermaPassword, gbc, 1, 3);

        // Riga 5 - Codice amministratore
        addComponent(createStyledLabel("Codice amministratore:"), gbc, 0, 4);
        addComponent(amministratore, gbc, 1, 4);

        // Pannello Sud - Bottoni
        registrati = createStyledButton("Registrati");
        indietro = createStyledButton("Indietro");
        sud.add(registrati);
        sud.add(indietro);

        // Aggiunta Pannelli
        add(nord, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(sud, BorderLayout.SOUTH);

        setVisible(false);
    }

    private void addComponent(JComponent comp, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        centro.add(comp, gbc);
    }

    public void attivaBottoni(ActionListener e) {
        registrati.addActionListener(e);
        indietro.addActionListener(e);
    }

    public JButton getRegistrati() { return registrati; }
    public JButton getIndietro() { return indietro; }

    // Registra un nuovo utente
    public void Registrazione() {

        // Se uno dei campi è vuoto ferma la registrazione
        if(username.getText().isEmpty() || email.getText().isEmpty() || password.getPassword().length == 0 || confermaPassword.getPassword().length == 0) {
            new Messaggio("Devi riempire tutti i campi");
            return;
        }

        // Controllo se nel campo email c'è qualcosa di simile a una email
        if (!email.getText().contains("@")) {
            new Messaggio("Email non valida");
            return;
        }

        // Se le password non corrispondono ferma la registrazione
        if(!Arrays.equals(password.getPassword(), confermaPassword.getPassword())) {
            new Messaggio("Le password inserite non corrispondono");
            return;
        }

        try {

            // Costruzione richiesta
            URL url = new URL("http://localhost:8080/api/auth/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Costruzione Json
            String json;
            if (amministratore.getText().equals("admin bello")) {
                json = "{ " +
                        "\"nome\": \"" + username.getText() + "\", " +
                        "\"email\": \"" + email.getText() + "\", " +
                        "\"password\": \"" + new String(password.getPassword()) + "\", " +
                        "\"permessi\": \"Admin\" }";
            } else {
                json = "{ " +
                        "\"nome\": \"" + username.getText() + "\", " +
                        "\"email\": \"" + email.getText() + "\", " +
                        "\"password\": \"" + new String(password.getPassword()) + "\", " +
                        "\"permessi\": \"User\" }";
            }

            // Invio richiesta
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Risposta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                new Messaggio("Registrazione avvenuta!");
            }

        } catch (Exception e) { new Messaggio("Errore: registrazione fallita"); }
    }
}
