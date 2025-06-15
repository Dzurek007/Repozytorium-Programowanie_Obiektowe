package com.example.airlinereservation.ui;

import com.example.airlinereservation.dao.UserDAO;
import com.example.airlinereservation.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistrationPanel extends JPanel {
    private JTextField tfUsername, tfEmail;
    private JPasswordField pfPassword, pfConfirmPassword;
    private UserDAO userDAO;

    public RegistrationPanel() {
        userDAO = new UserDAO();
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Username:"));
        tfUsername = new JTextField();
        add(tfUsername);

        add(new JLabel("Email:"));
        tfEmail = new JTextField();
        add(tfEmail);

        add(new JLabel("Password:"));
        pfPassword = new JPasswordField();
        add(pfPassword);

        add(new JLabel("Confirm Password:"));
        pfConfirmPassword = new JPasswordField();
        add(pfConfirmPassword);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(this::registerAction);
        add(btnRegister);

        JButton btnBack = new JButton("Back to Login");
        btnBack.addActionListener(e -> showLoginPanel());
        add(btnBack);
    }

    private void registerAction(ActionEvent e) {
        try {
            String username = tfUsername.getText();
            String email = tfEmail.getText();
            String password = new String(pfPassword.getPassword());
            String confirmPassword = new String(pfConfirmPassword.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // czy użytkownik  istnieje
            User existingUser = userDAO.getUserByUsername(username);
            if (existingUser != null) {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // rejestrujemy nowego użytkownika
            User newUser = new User(0, username, password, email);
            if (userDAO.registerUser(newUser)) {
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showLoginPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showLoginPanel() {

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new LoginPanel());
        frame.revalidate();
    }
}
