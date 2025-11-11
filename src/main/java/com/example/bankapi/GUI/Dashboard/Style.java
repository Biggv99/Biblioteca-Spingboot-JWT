package com.example.bankapi.GUI.Dashboard;

import javax.swing.*;
import java.awt.*;

public interface Style {

    default JTextField createTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return field;
    }

    default JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 100, 100));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Monospaced", Font.PLAIN, 12));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    default JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return label;
    }
}
