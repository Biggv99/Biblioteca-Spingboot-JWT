package com.example.bankapi.GUI.Dashboard.Libro;

import com.example.bankapi.GUI.Dashboard.Style;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminLibro extends JPanel implements Style {

    private final JButton aggiungi, rimuovi, modifica;

    public AdminLibro() {

        // Impostazioni layout
        setLayout(new BorderLayout());

        JPanel contenuto = new JPanel(new GridBagLayout());
        contenuto.setBackground(Color.LIGHT_GRAY);
        contenuto.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Componenti
        aggiungi = createStyledButton("Aggiungi Libro");
        rimuovi = createStyledButton("Rimuovi Libro");
        modifica = createStyledButton("Modifica Libro");

        addComponent(contenuto, aggiungi, gbc, 1, 0);

        addComponent(contenuto, createStyledLabel("Seleziona libro:"), gbc, 0, 1);
        addComponent(contenuto, rimuovi, gbc, 1, 1);

        addComponent(contenuto, createStyledLabel("Seleziona libro:"), gbc, 0, 2);
        addComponent(contenuto, modifica, gbc, 1, 2);

        add(contenuto, BorderLayout.CENTER);
    }

    private void addComponent(JPanel panel, JComponent comp, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(comp, gbc);
    }

    public void attivaBottoni(ActionListener e) {
        aggiungi.addActionListener(e);
        rimuovi.addActionListener(e);
        modifica.addActionListener(e);
    }

    public JButton getAggiungi() { return aggiungi; }
    public JButton getRimuovi() { return rimuovi; }
    public JButton getModifica() { return modifica; }
}
