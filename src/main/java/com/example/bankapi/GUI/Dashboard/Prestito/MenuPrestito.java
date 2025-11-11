package com.example.bankapi.GUI.Dashboard.Prestito;

import com.example.bankapi.GUI.Dashboard.Style;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPrestito extends JPanel implements Style {

    private final JTextField utente, libro, restituito;
    private final JButton cerca, report;

    public MenuPrestito() {

        // Imposta layout
        setLayout(new BorderLayout());

        // Pannello Nord - Report
        JPanel nord = new JPanel();
        nord.setBackground(Color.DARK_GRAY);
        nord.setBorder(BorderFactory.createEmptyBorder(100, 3, 3, 3));

        report = createStyledButton("Report dei prestiti");
        nord.add(report);

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
        utente = createTextField();
        libro = createTextField();
        restituito = createTextField();
        cerca = createStyledButton("Cerca!");

        addComponent(centro, createStyledLabel("CERCA PRESTITO:"), gbc, 1, 0);

        addComponent(centro, createStyledLabel("Utente:"), gbc, 0, 1);
        addComponent(centro, utente, gbc, 1, 1);

        addComponent(centro, createStyledLabel("Libro:"), gbc, 0, 2);
        addComponent(centro, libro, gbc, 1, 2);

        addComponent(centro, createStyledLabel("Restituito:"), gbc, 0, 3);
        addComponent(centro, restituito, gbc, 1, 3);

        addComponent(centro, cerca, gbc, 1, 4);

        // Aggiunta pannelli
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
        cerca.addActionListener(e);
        report.addActionListener(e);
    }

    public JButton getCerca() { return cerca; }
    public JButton getReport() { return report; }
    public String getUtente() { return utente.getText(); }
    public String getLibro() { return libro.getText(); }
    public String getRestituito() { return restituito.getText(); }
}
