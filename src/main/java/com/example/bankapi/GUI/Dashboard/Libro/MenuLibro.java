package com.example.bankapi.GUI.Dashboard.Libro;

import com.example.bankapi.config.SessioneUtente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuLibro extends JPanel {

    private final UtenteLibro utenteLibro;
    private final AdminLibro adminLibro;

    public MenuLibro() {

        setLayout(new GridLayout(2, 1));

        utenteLibro = new UtenteLibro();
        adminLibro = new AdminLibro();

        add(utenteLibro);
        add(adminLibro);

        setVisible(false);
    }

    public void attivaBottoni(ActionListener e){
        utenteLibro.attivaBottoni(e);
        adminLibro.attivaBottoni(e);
    }

    public JButton getUtentePrenota(){ return utenteLibro.getPrenota(); }
    public JButton getUtenteRestituisci(){ return utenteLibro.getRestituisci(); }
    public JButton getUtenteCerca(){ return utenteLibro.getCerca(); }
    public JButton getAdminAggiungi(){ return adminLibro.getAggiungi(); }
    public JButton getAdminRimuovi(){ return adminLibro.getRimuovi(); }
    public JButton getAdminModifica(){ return adminLibro.getModifica(); }

    public void checkPremessi(){

        adminLibro.setVisible(SessioneUtente.getInstance().getRuolo().equals("Admin"));
    }
}
