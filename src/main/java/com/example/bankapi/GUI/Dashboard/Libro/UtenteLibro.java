package com.example.bankapi.GUI.Dashboard.Libro;

import com.example.bankapi.GUI.Dashboard.Style;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UtenteLibro extends JPanel implements Style {

    private final JButton prenota, restituisci, cerca;

    public UtenteLibro() {

        // Impostazioni layout
        setLayout(new BorderLayout());

        JPanel contenuto = new JPanel(new GridBagLayout());
        contenuto.setBackground(Color.DARK_GRAY);
        contenuto.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Componenti
        prenota = createStyledButton("Prenota");
        restituisci = createStyledButton("Restituisci");
        cerca = createStyledButton("Cerca");

        addComponent(contenuto, createStyledLabel("Seleziona libro:"), gbc, 0, 0);
        addComponent(contenuto, prenota, gbc, 1, 0);

        addComponent(contenuto, restituisci, gbc, 1, 1);

        addComponent(contenuto, cerca, gbc, 1, 2);

        add(contenuto, BorderLayout.CENTER);
    }

    private void addComponent(JPanel panel, JComponent comp, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(comp, gbc);
    }

    public void attivaBottoni(ActionListener e) {
        prenota.addActionListener(e);
        restituisci.addActionListener(e);
        cerca.addActionListener(e);
    }

    public JButton getPrenota() { return prenota; }
    public JButton getRestituisci() { return restituisci; }
    public JButton getCerca() { return cerca; }
}
