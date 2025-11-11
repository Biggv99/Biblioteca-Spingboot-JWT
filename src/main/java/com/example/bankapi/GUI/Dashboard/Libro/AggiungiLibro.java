package com.example.bankapi.GUI.Dashboard.Libro;

import com.example.bankapi.GUI.Dashboard.Style;
import com.example.bankapi.GUI.Messaggi.Messaggio;
import com.example.bankapi.config.SessioneUtente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

public class AggiungiLibro extends JDialog implements Style {

    private final JTextField titolo, autore, genere, numeroCopie;
    private final JButton conferma, esci;
    private final JSpinner dataSpinner;

    public AggiungiLibro() {

        // Layout principale
        setLayout(new BorderLayout());

        // Pannello Nord - Etichetta
        JPanel nord = new JPanel();
        JLabel etichetta = createStyledLabel("Aggiungi libro");
        nord.setBackground(Color.BLACK);
        nord.add(etichetta);

        // Pannello Centro - Grid
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(Color.DARK_GRAY);
        centro.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Componenti
        autore = createTextField();
        titolo = createTextField();
        genere = createTextField();
        numeroCopie = createTextField();

        addComponent(centro, createStyledLabel("Titolo:"), gbc, 0, 0);
        addComponent(centro, titolo, gbc, 1, 0);

        addComponent(centro, createStyledLabel("Autore:"), gbc, 0, 1);
        addComponent(centro, autore, gbc, 1, 1);

        addComponent(centro, createStyledLabel("Genere:"), gbc, 0, 2);
        addComponent(centro, genere, gbc, 1, 2);

        dataSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dataSpinner, "dd/MM/yyyy");
        dataSpinner.setEditor(dateEditor);
        addComponent(centro, createStyledLabel("Anno:"), gbc, 0, 3);
        addComponent(centro, dataSpinner, gbc, 1, 3);

        addComponent(centro, createStyledLabel("Numero copie:"), gbc, 0, 4);
        addComponent(centro, numeroCopie, gbc, 1, 4);

        // Pannello Sud - Bottoni
        JPanel sud = new JPanel(new FlowLayout());
        sud.setBackground(Color.BLACK);

        conferma = createStyledButton("Aggiungi libro");
        esci = createStyledButton("Esci");

        sud.add(conferma);
        sud.add(esci);

        // Aggiungi Pannelli
        add(nord, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(sud, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void addComponent(JPanel panel, JComponent comp, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(comp, gbc);
    }

    public void attivaBottoni(ActionListener e){
        conferma.addActionListener(e);
        esci.addActionListener(e);
    }

    public void aggiungiLibro() {
        try {
            URL url = new URL("http://localhost:8080/api/libro/admin/aggiungi");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + SessioneUtente.getInstance().getToken());
            connection.setDoOutput(true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String data = dateFormat.format(dataSpinner.getValue());

            String json = "{"
                    + "\"titolo\": \"" + titolo.getText() + "\", "
                    + "\"autore\": \"" + autore.getText() + "\", "
                    + "\"genere\": \"" + genere.getText() + "\", "
                    + "\"anno\": \"" + data + "\", "
                    + "\"numeroCopie\": \"" + numeroCopie.getText() + "\""
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                new Messaggio("Libro aggiunto!");
            } else {
                new Messaggio("Inserimento libro fallito!");
            }
        } catch (Exception ex) {
            new Messaggio("Errore: non ho aggiunto il libro");
        }
    }
}
