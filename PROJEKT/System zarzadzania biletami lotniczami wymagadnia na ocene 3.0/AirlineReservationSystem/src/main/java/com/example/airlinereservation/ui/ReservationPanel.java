package com.example.airlinereservation.ui;

import com.example.airlinereservation.dao.PassengerDAO;
import com.example.airlinereservation.dao.FlightDAO;
import com.example.airlinereservation.dao.ReservationDAO;
import com.example.airlinereservation.models.Reservation;
import com.example.airlinereservation.models.Passenger;
import com.example.airlinereservation.models.Flight;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReservationPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    private final JTextField tfPassengerId;
    private final JTextField tfFlightId;
    private final JTextField tfDate;
    private final JTextField tfSeats;
    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final PassengerDAO passengerDAO = new PassengerDAO();
    private final FlightDAO flightDAO = new FlightDAO();

    public ReservationPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[] {
                "ID", "ID Pasażera", "ID Lotu", "Data", "Miejsce w Samolocie "
        }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 5, 5, 5));
        tfPassengerId = new JTextField();
        tfFlightId = new JTextField();
        tfDate = new JTextField();
        tfSeats = new JTextField();
        form.add(new JLabel("ID Pasażera"));
        form.add(new JLabel("ID Lotu"));
        form.add(new JLabel("Data (yyyy-MM-dd)"));
        form.add(new JLabel("Miejsce w Samolocie (1-100)"));
        form.add(new JLabel());
        form.add(tfPassengerId);
        form.add(tfFlightId);
        form.add(tfDate);
        form.add(tfSeats);
        JButton btnAdd = new JButton("Dodaj Rezerwację");
        btnAdd.addActionListener(e -> reserveFlight());
        form.add(btnAdd);
        add(form, BorderLayout.NORTH);

        loadTable();

        table.getSelectionModel().addListSelectionListener((ListSelectionListener) e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int r = table.getSelectedRow();
                tfPassengerId.setText(model.getValueAt(r, 1).toString());
                tfFlightId.setText(model.getValueAt(r, 2).toString());
                tfDate.setText(model.getValueAt(r, 3).toString());
                tfSeats.setText(model.getValueAt(r, 4).toString());
            }
        });
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Reservation> list = reservationDAO.getAll();
            for (Reservation r : list) {
                model.addRow(new Object[] {
                        r.getId(),
                        r.getPassengerId(),
                        r.getFlightId(),
                        r.getReservationDate().toString(),
                        r.getSeats()
                });
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void reserveFlight() {
        try {
            int passengerId = Integer.parseInt(tfPassengerId.getText());
            int flightId = Integer.parseInt(tfFlightId.getText());
            LocalDate date = LocalDate.parse(tfDate.getText());
            int seats = Integer.parseInt(tfSeats.getText());


            Passenger passenger = passengerDAO.getAll().stream()
                    .filter(p -> p.getId() == passengerId)
                    .findFirst().orElse(null);

            Flight flight = flightDAO.getAll().stream()
                    .filter(f -> f.getId() == flightId)
                    .findFirst().orElse(null);

            if (passenger != null && flight != null) {
                Reservation reservation = new Reservation(0, passengerId, flightId, date, seats);
                reservationDAO.insert(reservation);

                JOptionPane.showMessageDialog(this, "Rezerwacja została pomyślnie dodana!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Nie znaleziono pasażera lub lotu!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
    }
}
