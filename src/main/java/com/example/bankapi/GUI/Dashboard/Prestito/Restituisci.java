package com.example.bankapi.GUI.Dashboard.Prestito;

import com.example.bankapi.GUI.Dashboard.Style;
import com.example.bankapi.GUI.Dashboard.Tabella;
import com.example.bankapi.config.SessioneUtente;
import com.example.bankapi.model.Prestito;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Restituisci extends JDialog implements Style {

    public Restituisci(ArrayList<Prestito> prestiti, Tabella tabella) {

        prestiti.removeIf(p -> !p.getUtente().equals(SessioneUtente.getInstance().getNome()) || !p.getRestituito().equalsIgnoreCase("No"));

        String[] columnNames = {"ID", "Utente", "Libro", "Data Inizio", "Data Fine", "Restituito", "Bottone"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Object value = getValueAt(row, column);
                if (value instanceof JButton) {
                    return (JButton) value;
                }
                return super.prepareRenderer(renderer, row, column);
            }
        };

        // Aggiunge righe
        for (Prestito p : prestiti) {
            JButton bottone = createStyledButton("Restituisci!");
            bottone.addActionListener(e -> {
                if (p.getRestituito().equalsIgnoreCase("No")) {
                    tabella.restituisci(String.valueOf(p.getId()), p.getLibro(), "si", 1);
                    p.setRestituito("si");
                } else {
                    tabella.restituisci(String.valueOf(p.getId()), p.getLibro(), "No", -1);
                    p.setRestituito("No");
                }

                model.setValueAt(p.getRestituito(), table.getSelectedRow(), 5);
                table.repaint();
            });

            model.addRow(new Object[]{
                    p.getId(),
                    p.getUtente(),
                    p.getLibro(),
                    p.getDataInizio(),
                    p.getDataFine(),
                    p.getRestituito(),
                    bottone
            });
        }

        // Mouse listener per gestire il click sui bottoni
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / table.getRowHeight();

                if (row < table.getRowCount() && row >= 0 && column == 6) {
                    Object value = table.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                    }
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setSize(980, 690);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
