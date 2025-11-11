package com.example.bankapi.GUI.Dashboard.Prestito;

import com.example.bankapi.model.Prestito;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Reportistic extends JDialog {

    public Reportistic(ArrayList<Prestito> lista) {

        setLayout(new GridLayout(1, 2));

        // REPORT LIBRI
        ArrayList<String> libri = new ArrayList<>();
        ArrayList<Integer> conteggiLibri = new ArrayList<>();

        for (Prestito p : lista) {
            int index = libri.indexOf(p.getLibro());

            if (index != -1) {
                conteggiLibri.set(index, conteggiLibri.get(index) + 1);
            } else {
                libri.add(p.getLibro());
                conteggiLibri.add(1);
            }
        }

        // Bubble sort libri
        for (int i = 0; i < conteggiLibri.size() - 1; i++) {
            for (int j = i + 1; j < conteggiLibri.size(); j++) {
                if (conteggiLibri.get(j) > conteggiLibri.get(i)) {
                    int tempCount = conteggiLibri.get(i);
                    conteggiLibri.set(i, conteggiLibri.get(j));
                    conteggiLibri.set(j, tempCount);

                    String tempTitle = libri.get(i);
                    libri.set(i, libri.get(j));
                    libri.set(j, tempTitle);
                }
            }
        }

        // REPORT UTENTI
        ArrayList<String> utenti = new ArrayList<>();
        ArrayList<Integer> conteggiUtenti = new ArrayList<>();

        for (Prestito p : lista) {
            int index = utenti.indexOf(p.getUtente());

            if (index != -1) {
                conteggiUtenti.set(index, conteggiUtenti.get(index) + 1);
            } else {
                utenti.add(p.getUtente());
                conteggiUtenti.add(1);
            }
        }

        // Bubble sort utenti
        for (int i = 0; i < conteggiUtenti.size() - 1; i++) {
            for (int j = i + 1; j < conteggiUtenti.size(); j++) {
                if (conteggiUtenti.get(j) > conteggiUtenti.get(i)) {
                    int tempCount = conteggiUtenti.get(i);
                    conteggiUtenti.set(i, conteggiUtenti.get(j));
                    conteggiUtenti.set(j, tempCount);

                    String tempUser = utenti.get(i);
                    utenti.set(i, utenti.get(j));
                    utenti.set(j, tempUser);
                }
            }
        }

        // CREAZIONE TABELLA LIBRI
        String[] colonneLibri = {"Libro", "Numero Prestiti"};
        Object[][] datiLibri = new Object[libri.size()][2];

        for (int i = 0; i < libri.size(); i++) {
            datiLibri[i][0] = libri.get(i);
            datiLibri[i][1] = conteggiLibri.get(i);
        }
        JTable tabellaLibri = new JTable(datiLibri, colonneLibri);

        // CREAZIONE TABELLA UTENTI
        String[] colonneUtenti = {"Utente", "Numero Prestiti"};
        Object[][] datiUtenti = new Object[utenti.size()][2];

        for (int i = 0; i < utenti.size(); i++) {
            datiUtenti[i][0] = utenti.get(i);
            datiUtenti[i][1] = conteggiUtenti.get(i);
        }
        JTable tabellaUtenti = new JTable(datiUtenti, colonneUtenti);

        // Agguungo al JDialog
        add(new JScrollPane(tabellaLibri));
        add(new JScrollPane(tabellaUtenti));

        // Impostazione finali
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}

