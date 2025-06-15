package com.example.airlinereservation.ui;

import com.example.airlinereservation.dao.PassengerDAO;
import com.example.airlinereservation.models.Passenger;
import com.example.airlinereservation.models.VIPPassenger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PassengerPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField tfFirst, tfLast, tfPassport, tfFF, tfMiles, tfVip;
    private PassengerDAO dao = new PassengerDAO();

    public PassengerPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[]{
                "ID", "Imię", "Nazwisko", "Paszport", "Numer programu lojalnościowego", "Wiek", "VIP"
        }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 7));
        tfFirst = new JTextField(); tfLast = new JTextField();
        tfPassport = new JTextField(); tfFF = new JTextField();
        tfMiles = new JTextField(); tfVip = new JTextField();
        form.add(new JLabel("Imię")); form.add(new JLabel("Nazwisko"));
        form.add(new JLabel("Paszport")); form.add(new JLabel("Numer programu lojalnościowego"));
        form.add(new JLabel("Wiek")); form.add(new JLabel("VIP"));
        form.add(new JLabel());
        form.add(tfFirst); form.add(tfLast); form.add(tfPassport);
        form.add(tfFF); form.add(tfMiles); form.add(tfVip);
        JButton btnAdd = new JButton("Dodaj");
        form.add(btnAdd);
        add(form, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        JButton btnUpd = new JButton("Aktualizuj"), btnDel = new JButton("Usuń");
        buttons.add(btnUpd); buttons.add(btnDel);
        add(buttons, BorderLayout.SOUTH);

        loadTable();

        btnAdd.addActionListener(e -> {
            try {
                Passenger p;
                if (!tfVip.getText().isEmpty()) {
                    p = new VIPPassenger(0, tfFirst.getText(), tfLast.getText(),
                            tfPassport.getText(), tfFF.getText(),
                            Integer.parseInt(tfMiles.getText()), tfVip.getText());
                } else {
                    p = new Passenger(0, tfFirst.getText(), tfLast.getText(), tfPassport.getText());
                }
                dao.insert(p);
                loadTable();
            } catch (Exception ex) { showError(ex); }
        });
        btnUpd.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                if (row < 0) return;
                int id = (int) model.getValueAt(row, 0);
                Passenger p;
                if (!tfVip.getText().isEmpty()) {
                    p = new VIPPassenger(id, tfFirst.getText(), tfLast.getText(),
                            tfPassport.getText(), tfFF.getText(),
                            Integer.parseInt(tfMiles.getText()), tfVip.getText());
                } else {
                    p = new Passenger(id, tfFirst.getText(), tfLast.getText(), tfPassport.getText());
                }
                dao.update(p);
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
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int r = table.getSelectedRow();
                tfFirst.setText(model.getValueAt(r, 1).toString());
                tfLast.setText(model.getValueAt(r, 2).toString());
                tfPassport.setText(model.getValueAt(r, 3).toString());
                tfFF.setText(model.getValueAt(r, 4).toString());
                tfMiles.setText(model.getValueAt(r, 5).toString());
                tfVip.setText(model.getValueAt(r, 6).toString());
            }
        });
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Passenger> list = dao.getAll();
            for (Passenger p : list) {
                if (p instanceof VIPPassenger) {
                    VIPPassenger vp = (VIPPassenger) p;
                    model.addRow(new Object[]{
                            vp.getId(), vp.getFirstName(), vp.getLastName(),
                            vp.getPassport(), vp.getFrequentFlyerNumber(),
                            vp.getMiles(), vp.getVipLevel()
                    });
                } else {
                    model.addRow(new Object[]{
                            p.getId(), p.getFirstName(), p.getLastName(),
                            p.getPassport(), "", 0, ""
                    });
                }
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
    }
}
