package com.example.airlinereservation.dao;

import com.example.airlinereservation.models.Reservation;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class ReservationDAO {

    //  metoda może rzucać wyjątek
    public void insert(Reservation r) throws Exception {
        String query = "INSERT INTO reservations (passenger_id, flight_id, reservation_date, seats) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, r.getPassengerId());
            stmt.setInt(2, r.getFlightId());
            stmt.setDate(3, Date.valueOf(r.getReservationDate()));
            stmt.setInt(4, r.getSeats());

            stmt.executeUpdate();
        }
    }

    //  metoda może rzucać wyjątek
    public List<Reservation> getAll() throws Exception {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int passengerId = rs.getInt("passenger_id");
                int flightId = rs.getInt("flight_id");
                LocalDate reservationDate = rs.getDate("reservation_date").toLocalDate();
                int seats = rs.getInt("seats");
                reservations.add(new Reservation(id, passengerId, flightId, reservationDate, seats));
            }
        }
        return reservations;
    }
}
