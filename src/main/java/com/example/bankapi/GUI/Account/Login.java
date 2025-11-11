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

public class Login extends JPanel implements Style {

    private final JButton indietro, accedi;
    private final JTextField username;
    private final JPasswordField password;
    private final JPanel centro;

    public Login() {

        // Impostazione Layout
        setLayout(new BorderLayout());

        // Creazione Pannelli
        JPanel nord = new JPanel(new FlowLayout());
        nord.setBackground(Color.BLACK);

        centro = new JPanel(new GridBagLayout());
        centro.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel sud = new JPanel(new FlowLayout());
        sud.setBackground(Color.BLACK);

        // Pannello Nord - Titolo
        JLabel titolo = createStyledLabel("ACCESSO");
        titolo.setFont(new Font("Monospaced", Font.PLAIN, 48));
        nord.add(titolo);

        // Pannello Centro - Componenti
        username = createTextField();
        password = new JPasswordField();

        // Riga 1 - Username
        addComponent(createStyledLabel("Username:"), gbc, 0, 0);
        addComponent(username, gbc, 1, 0);

        // Riga 2 - Password
        addComponent(createStyledLabel("Password:"), gbc, 0, 1);
        addComponent(password, gbc, 1, 1);

        // Pannello Sud - Bottoni
        accedi = createStyledButton("Accedi");
        indietro = createStyledButton("Indietro");
        sud.add(accedi);
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
        indietro.addActionListener(e);
        accedi.addActionListener(e);
    }

    public JButton getIndietro() { return indietro; }
    public JButton getAccedi() { return accedi; }

    // Accesso con l'utente
    public boolean Accesso() {

        // Se uno dei campi è vuoto ferma il login
        if (username.getText().isEmpty() || password.getPassword().length == 0) {
            new Messaggio("Devi riempire tutti i campi");
            return false;
        }

        try {

            // Costruzione richiesta
            URL url = new URL("http://localhost:8080/api/auth/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Costruzione Json
            String json =   "{ \"nome\": \"" + username.getText() + "\", " +
                            "\"password\": \"" + new String(password.getPassword()) + "\" }";

            // Invio richiesta
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Risposta
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) { return false; }
    }
}
