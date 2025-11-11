package com.example.bankapi.GUI.Dashboard;

import com.example.bankapi.config.SessioneUtente;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Opzioni extends JPanel {

    private final JLabel nome, permessi;
    private final JMenuItem esci, esporta, utenti, libri, prestiti, istruzioni;

    public Opzioni() {

        setLayout(null);

        // Pannello Sinistra
        JPanel sinistra = new JPanel(null);
        sinistra.setBounds(0, 0, 300, 30);

        // Stilizza JMenuBar
        JMenuBar opzioni = new JMenuBar();
        opzioni.setOpaque(true);
        opzioni.setBounds(0, 0, 300, 32);

        // Stilizza i JMenu
        JMenu account = new JMenu("Account");
        JMenu file = new JMenu("File");
        JMenu visualizza = new JMenu("Visualizza");

        JMenu[] menus = {account, file, visualizza};
        for (JMenu menu : menus) {
            menu.setOpaque(true);
            menu.setBackground(new Color(0, 100, 100));
            menu.setForeground(Color.WHITE);
            menu.setPreferredSize(new Dimension(100, 30));
            menu.setBorder(new LineBorder(Color.BLACK, 1, true));
        }

        // Stilizza i JMenuItem
        nome = new JLabel(SessioneUtente.getInstance().getNome());
        permessi = new JLabel(SessioneUtente.getInstance().getRuolo());
        esci = new JMenuItem("esci");
        esporta = new JMenuItem("esporta");
        utenti = new JMenuItem("visualizza utenti");
        libri = new JMenuItem("visualizza libri");
        prestiti = new JMenuItem("visualizza prestiti");
        istruzioni = new JMenuItem("istruzioni");

        JMenuItem[] menuItem = {esci, esporta, utenti, libri, prestiti, istruzioni};
        for (JMenuItem menu : menuItem) {
            menu.setOpaque(true);
            menu.setBackground(new Color(0, 100, 100));
            menu.setForeground(Color.WHITE);
            menu.setPreferredSize(new Dimension(150, 30));
            menu.setBorder(new LineBorder(Color.BLACK, 1, true));
        }

        account.add(nome);
        account.add(permessi);
        account.add(esci);
        file.add(esporta);
        visualizza.add(utenti);
        visualizza.add(libri);
        visualizza.add(prestiti);
        visualizza.add(istruzioni);

        opzioni.add(account);
        opzioni.add(file);
        opzioni.add(visualizza);

        sinistra.add(opzioni);

        // Pannello destra
        JPanel destra = new JPanel();
        destra.setBounds(300, 0, 980, 30);
        destra.setBackground(Color.BLACK);

        // Aggiunta pannelli
        add(sinistra);
        add(destra);

        setVisible(false);
    }


    public void setAccount(){
        nome.setText(SessioneUtente.getInstance().getNome());
        permessi.setText(SessioneUtente.getInstance().getRuolo());
    }

    public void checkRuolo(){
        if (!SessioneUtente.getInstance().getRuolo().equals("Admin")){
            utenti.setVisible(false);
            prestiti.setVisible(false);
        } else {
            utenti.setVisible(true);
            prestiti.setVisible(true);
        }
    }

    public void attivaBottoni(ActionListener e){
        esci.addActionListener(e);
        esporta.addActionListener(e);
        utenti.addActionListener(e);
        libri.addActionListener(e);
        prestiti.addActionListener(e);
        istruzioni.addActionListener(e);
    }

    public JMenuItem getEsci() { return esci; }
    public JMenuItem getEsporta() { return esporta; }
    public JMenuItem getUtenti() { return utenti; }
    public JMenuItem getLibri() { return libri; }
    public JMenuItem getPrestiti() { return prestiti; }
    public JMenuItem getIstruzioni() { return istruzioni; }
}
