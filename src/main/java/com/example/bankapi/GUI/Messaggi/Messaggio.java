package com.example.bankapi.GUI.Messaggi;

import com.example.bankapi.GUI.Dashboard.Style;
import javax.swing.*;
import java.awt.*;

public class Messaggio extends JDialog implements Style {

    public Messaggio(String testo) {

        super((Frame) null, true);
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        JLabel label = createStyledLabel(testo);
        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton okay = createStyledButton("okay");
        okay.addActionListener(e -> dispose());

        add(label, BorderLayout.CENTER);
        add(okay, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
