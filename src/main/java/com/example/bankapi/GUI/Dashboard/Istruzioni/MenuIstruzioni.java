package com.example.bankapi.GUI.Dashboard.Istruzioni;

import javax.swing.*;
import java.awt.*;

public class MenuIstruzioni extends JPanel {

    String testo = """
                 ISTRUZIONI

        [ Account ]
        Contiene le informazioni sull'utente attuale e permette di effettuare il logout.

        [ Esporta ]
        Esporta in un file la tabella visibile sulla destra.

        [ Visualizza ]
        Consente di selezionare quale lista visualizzare dal database.
        Una volta selezionata, si apriranno i menu dedicati.
        Cliccare nuovamente sulla stessa voce resetta la tabella.

        [ Seleziona ... ]
        Quando appare questo messaggio, è necessario selezionare (cliccare) una riga della tabella.
        """;

    public MenuIstruzioni() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.DARK_GRAY);

        JTextArea istruzioni = new JTextArea();
        istruzioni.setText(testo);
        istruzioni.setBackground(Color.DARK_GRAY);
        istruzioni.setForeground(Color.WHITE);
        istruzioni.setEditable(false);
        istruzioni.setLineWrap(true);
        istruzioni.setWrapStyleWord(true);
        istruzioni.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(istruzioni, BorderLayout.CENTER);

        setVisible(false);
    }
}

