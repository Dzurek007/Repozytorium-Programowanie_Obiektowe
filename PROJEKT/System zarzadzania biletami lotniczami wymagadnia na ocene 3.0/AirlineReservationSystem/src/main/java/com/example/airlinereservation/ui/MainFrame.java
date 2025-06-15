package com.example.airlinereservation.ui;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {

        setTitle("System Rezerwacji Lotów");
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Pasażerowie", new PassengerPanel());
        tabs.add("Loty", new FlightPanel());
        tabs.add("Rezerwacje", new ReservationPanel());
        add(tabs);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // panel logowania
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150);
        loginFrame.add(new LoginPanel());
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }
}
