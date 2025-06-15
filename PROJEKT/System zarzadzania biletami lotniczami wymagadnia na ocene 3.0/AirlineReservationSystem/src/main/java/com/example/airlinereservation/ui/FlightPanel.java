package com.example.airlinereservation.ui;

import com.example.airlinereservation.dao.FlightDAO;
import com.example.airlinereservation.models.Flight;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    private final JTextField tfFlightNumber;
    private final JTextField tfOrigin;
    private final JTextField tfDestination;
    private final JTextField tfDeparture;
    private final JTextField tfArrival;
    private final FlightDAO dao = new FlightDAO();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FlightPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[]{
                "ID", "Numer lotu", "Wylot z:", "Przylot do:", "Wylot", "Przylot"
        }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 6, 5, 5));
        tfFlightNumber = new JTextField(); tfOrigin = new JTextField();
        tfDestination = new JTextField(); tfDeparture = new JTextField();
        tfArrival = new JTextField();
        form.add(new JLabel("Numer lotu"));   form.add(new JLabel("Wylot z:"));
        form.add(new JLabel("Przylot do:")); form.add(new JLabel("Wylot (yyyy-MM-dd HH:mm)"));
        form.add(new JLabel("Przylot (yyyy-MM-dd HH:mm)")); form.add(new JLabel());
        form.add(tfFlightNumber); form.add(tfOrigin); form.add(tfDestination);
        form.add(tfDeparture);    form.add(tfArrival);
        JButton btnAdd = new JButton("Dodaj");
        form.add(btnAdd);
        add(form, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        JButton btnUpd = new JButton("Aktualizuj");
        JButton btnDel = new JButton("Usuń");
        buttons.add(btnUpd);
        buttons.add(btnDel);
        add(buttons, BorderLayout.SOUTH);

        // załadujemy dane, podłączymy listener-y
        loadTable();

        btnAdd.addActionListener(e -> {
            try {
                LocalDateTime dep = LocalDateTime.parse(tfDeparture.getText(), dtf);
                LocalDateTime arr = LocalDateTime.parse(tfArrival.getText(), dtf);
                Flight f = new Flight(0,
                        tfFlightNumber.getText(),
                        tfOrigin.getText(),
                        tfDestination.getText(),
                        dep, arr);
                dao.insert(f);
                loadTable();
            } catch (Exception ex) { showError(ex); }
        });

        btnUpd.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                if (row < 0) return;
                int id = (int) model.getValueAt(row, 0);
                LocalDateTime dep = LocalDateTime.parse(tfDeparture.getText(), dtf);
                LocalDateTime arr = LocalDateTime.parse(tfArrival.getText(), dtf);
                Flight f = new Flight(id,
                        tfFlightNumber.getText(),
                        tfOrigin.getText(),
                        tfDestination.getText(),
                        dep, arr);
                dao.update(f);
                loadTable();
            } catch (Exception ex) { showError(ex); }
        });

        btnDel.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                if (row < 0) return;
                int id = (int) model.getValueAt(row, 0);
                dao.delete(id);
                loadTable();
            } catch (Exception ex) { showError(ex); }
        });

        table.getSelectionModel().addListSelectionListener((ListSelectionListener) e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int r = table.getSelectedRow();
                tfFlightNumber.setText(model.getValueAt(r, 1).toString());
                tfOrigin.setText(model.getValueAt(r, 2).toString());
                tfDestination.setText(model.getValueAt(r, 3).toString());
                tfDeparture.setText(model.getValueAt(r, 4).toString());
                tfArrival.setText(model.getValueAt(r, 5).toString());
            }
        });
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Flight> list = dao.getAll();
            for (Flight f : list) {
                model.addRow(new Object[]{
                        f.getId(),
                        f.getFlightNumber(),
                        f.getOrigin(),
                        f.getDestination(),
                        f.getDepartureTime().format(dtf),
                        f.getArrivalTime().format(dtf)
                });
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
    }
}
