package com.example.airlinereservation.ui;

import com.example.airlinereservation.dao.UserDAO;
import com.example.airlinereservation.models.User;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField tfUsername;
    private JPasswordField pfPassword;

    public LoginPanel() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        tfUsername = new JTextField();
        add(tfUsername);

        add(new JLabel("Password:"));
        pfPassword = new JPasswordField();
        add(pfPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            if (authenticateUser(tfUsername.getText(), new String(pfPassword.getPassword()))) {
                // Jeśli logowanie się uda, otwórz MainFrame
                JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Pobieramy referencję do okna logowania
                loginFrame.dispose(); // Zamykamy okno logowania

                // Otwórz główny panel aplikacji
                new MainFrame().setVisible(true);
                JOptionPane.showMessageDialog(this, "Login Successful!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Błędne dane logowania!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnLogin);


        JButton btnRegister = new JButton("Zarejestruj się");
        btnRegister.addActionListener(e -> {
            // Przełączamy widok na panel rejestracji
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new RegistrationPanel());  // Przełączamy na panel rejestracji
            frame.revalidate();
        });
        add(btnRegister);
    }

    private boolean authenticateUser(String username, String password) {
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return true;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Błąd bazy danych: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
