package com.example.bankapi.GUI.Messaggi;

import com.example.bankapi.GUI.Dashboard.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Conferma extends JDialog implements Style {

    private final JButton si, no;

    public Conferma(String testo) {
        
        setLayout(new BorderLayout());

        JLabel label = createStyledLabel(testo);
        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        si = createStyledButton("si");
        no = createStyledButton("no");

        JPanel bottoniPanel = new JPanel(new FlowLayout());
        bottoniPanel.add(si);
        bottoniPanel.add(no);

        add(label, BorderLayout.CENTER);
        add(bottoniPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void attivaBottoni(ActionListener e) {
        si.addActionListener(e);
        no.addActionListener(e);
    }
}
