package com.example.bankapi.GUI.Dashboard.Libro;

import com.example.bankapi.GUI.Dashboard.Style;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CercaLibro extends JDialog implements Style {

    private final JTextField titolo, autore, genere, numeroCopie;
    private final JButton conferma, esci;

    public CercaLibro() {

        setLayout(new BorderLayout());

        // Pannello Nord - Etichetta
        JPanel nord = new JPanel();
        JLabel etichetta = createStyledLabel("Cerca libro");
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

        titolo = createTextField();
        autore = createTextField();
        genere = createTextField();
        numeroCopie = createTextField();

        addComponent(centro, createStyledLabel("Titolo:"), gbc, 0, 0);
        addComponent(centro, titolo, gbc, 1, 0);

        addComponent(centro, createStyledLabel("Autore:"), gbc, 0, 1);
        addComponent(centro, autore, gbc, 1, 1);

        addComponent(centro, createStyledLabel("Genere:"), gbc, 0, 2);
        addComponent(centro, genere, gbc, 1, 2);

        addComponent(centro, createStyledLabel("Numero copie:"), gbc, 0, 3);
        addComponent(centro, numeroCopie, gbc, 1, 3);

        // Pannello Sud - Bottoni
        JPanel sud = new JPanel(new FlowLayout());
        sud.setBackground(Color.BLACK);

        conferma = createStyledButton("Cerca libro");
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

    public String getTitolo() { return titolo.getText(); }
    public String getAutore() { return autore.getText(); }
    public String getGenere() { return genere.getText(); }
    public String getNumeroCopie() { return numeroCopie.getText(); }
}
