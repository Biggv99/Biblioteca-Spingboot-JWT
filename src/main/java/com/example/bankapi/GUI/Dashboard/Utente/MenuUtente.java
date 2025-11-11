package com.example.bankapi.GUI.Dashboard.Utente;

import com.example.bankapi.GUI.Dashboard.Style;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuUtente extends JPanel implements Style {

    private final JButton elimina, cerca;
    private final JTextField nome, email, ruolo;

    public MenuUtente() {

        // Impostazioni layout
        setLayout(new BorderLayout());

        // Pannello Nord - Eliminazione
        JPanel nord = new JPanel(new GridLayout(1, 2));
        nord.setBackground(Color.DARK_GRAY);
        nord.setBorder(BorderFactory.createEmptyBorder(100, 3, 3, 3));

        elimina = createStyledButton("Elimina");
        nord.add(createStyledLabel("Seleziona utente:"));
        nord.add(elimina);

        // Pannello Centro - Grid Cerca
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(Color.DARK_GRAY);
        centro.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Componenti
        nome = createTextField();
        email = createTextField();
        ruolo = createTextField();
        cerca = createStyledButton("Cerca!");

        addComponent(centro, createStyledLabel("CERCA UTENTI:"), gbc, 1, 0);

        addComponent(centro, createStyledLabel("Nome:"), gbc, 0, 1);
        addComponent(centro, nome, gbc, 1, 1);

        addComponent(centro, createStyledLabel("Email:"), gbc, 0, 2);
        addComponent(centro, email, gbc, 1, 2);

        addComponent(centro, createStyledLabel("Ruolo:"), gbc, 0, 3);
        addComponent(centro, ruolo, gbc, 1, 3);

        addComponent(centro, cerca, gbc, 1, 4);

        // Aggiunta pannelli al frame
        add(nord, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);

        setVisible(false);
    }

    private void addComponent(JPanel panel, JComponent comp, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(comp, gbc);
    }

    public void attivaBottoni(ActionListener e) {
        elimina.addActionListener(e);
        cerca.addActionListener(e);
    }

    public JButton getElimina() { return elimina; }
    public JButton getCerca() { return cerca; }
    public String getNome() { return nome.getText(); }
    public String getEmail() { return email.getText(); }
    public String getRuolo() { return ruolo.getText(); }
}
