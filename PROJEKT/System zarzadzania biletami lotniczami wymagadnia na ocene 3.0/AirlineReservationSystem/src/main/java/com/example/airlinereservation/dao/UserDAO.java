package com.example.airlinereservation.dao;

import com.example.airlinereservation.models.User;
import java.sql.*;

public class UserDAO {

    // Metoda rejestracji użytkownika - deklarujemy wyjątek
    public boolean registerUser(User user) throws Exception {
        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            return stmt.executeUpdate() > 0;
        }
    }

    // Metoda do pobrania użytkownika z bazy danych
    public User getUserByUsername(String username) throws Exception {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                }
                return null;
            }
        }
    }
}
