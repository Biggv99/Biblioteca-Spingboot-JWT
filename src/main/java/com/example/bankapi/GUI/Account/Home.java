package com.example.bankapi.GUI.Account;

import com.example.bankapi.GUI.Dashboard.Style;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Home extends JPanel implements Style {

    private final JButton login, register;

    public Home() {

        // Impostazione Layout
        setLayout(new BorderLayout());

        // Creazione Pannelli
        JPanel nord = new JPanel(new FlowLayout());
        nord.setBackground(Color.BLACK);

        JPanel centro = new JPanel();
        centro.setBackground(Color.DARK_GRAY);

        JPanel sud = new JPanel(new FlowLayout());
        sud.setBackground(Color.BLACK);

        // Pannello Centro - Titolo
        JLabel titolo = createStyledLabel("BENVENUTO");
        titolo.setFont(new Font("Monospaced", Font.PLAIN, 48));
        nord.add(titolo);

        // Pannello Sud - Bottoni
        login = createStyledButton("Accedi");
        register = createStyledButton("Registrati");
        sud.add(login);
        sud.add(register);

        // Aggiunta Pannelli
        add(nord, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(sud, BorderLayout.SOUTH);

        setOpaque(false);
        setVisible(true);
    }

    public void attivaBottoni(ActionListener e) {
        login.addActionListener(e);
        register.addActionListener(e);
    }

    public JButton getLogin() { return login; }
    public JButton getRegister() { return register; }
}
