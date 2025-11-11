package com.example.bankapi.config;

import com.example.bankapi.GUI.Messaggi.Messaggio;
import com.example.bankapi.model.*;
import java.io.*;
import java.util.ArrayList;

public interface SaveLoad {

    // Esporta una lista di utenti
    default void esportaUtenti(ArrayList<Utente> copia, String file){

        try {

            FileWriter writer = new FileWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(writer);

            int i = 0;
            while (i != copia.size()){

                Utente u = copia.get(i);
                String linea = u.getId() + " " + u.getNome() + " " + u.getEmail() + " " + u.getPermessi();

                buffWriter.write(linea);
                buffWriter.newLine();
                buffWriter.flush();
                i++;
            }

            if(copia.isEmpty()){
                buffWriter.write("La lista è vuota");
                buffWriter.newLine();
                buffWriter.flush();
            }

            writer.close();
        } catch(IOException e){ new Messaggio("Errore durante l'esportazione utenti"); }
    }

    // Esporta una lista di libri
    default void esportaLibri(ArrayList<Libro> copia, String file){

        try {

            FileWriter writer = new FileWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(writer);

            int i = 0;
            while (i != copia.size()){

                Libro l = copia.get(i);
                String linea =
                                l.getId() + "  " + l.getTitolo() + "  " + l.getAutore() + " " +
                                l.getGenere() + " " + l.getAnno() + " " + l.getNumeroCopie();

                buffWriter.write(linea);
                buffWriter.newLine();
                buffWriter.flush();
                i++;
            }

            if(copia.isEmpty()){
                buffWriter.write("La lista è vuota");
                buffWriter.newLine();
                buffWriter.flush();
            }

            writer.close();
        } catch(IOException e){ new Messaggio("Errore durante l'esportazione libri"); }
    }

    // Esporta una lista di prestiti
    default void esportaPresiti(ArrayList<Prestito> copia, String file){

        try {

            FileWriter writer = new FileWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(writer);

            int i = 0;
            while (i != copia.size()){

                Prestito p = copia.get(i);
                String linea =
                                p.getId() + "  " + p.getUtente() + "  " + p.getLibro() + " " +
                                p.getDataInizio() + " " + p.getDataFine() + " " + p.getRestituito();

                buffWriter.write(linea);
                buffWriter.newLine();
                buffWriter.flush();
                i++;
            }

            if(copia.isEmpty()){
                buffWriter.write("La lista è vuota");
                buffWriter.newLine();
                buffWriter.flush();
            }

            writer.close();
        } catch(IOException e){ new Messaggio("Errore durante l'esportazione prenotazioni"); }
    }
}
