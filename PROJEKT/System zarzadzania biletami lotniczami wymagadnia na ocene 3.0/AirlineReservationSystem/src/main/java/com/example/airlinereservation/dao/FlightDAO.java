package com.example.airlinereservation.dao;

import com.example.airlinereservation.models.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    public List<Flight> getAll() throws Exception {
        Connection c = DBConnection.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM flights");
        List<Flight> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String fn = rs.getString("flight_number");
            String orig = rs.getString("origin");
            String dest = rs.getString("destination");
            LocalDateTime dep = rs.getTimestamp("departure_time").toLocalDateTime();
            LocalDateTime arr = rs.getTimestamp("arrival_time").toLocalDateTime();
            list.add(new Flight(id, fn, orig, dest, dep, arr));
        }
        rs.close();
        st.close();
        return list;
    }

    public void insert(Flight f) throws Exception {
        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "INSERT INTO flights(flight_number,origin,destination,departure_time,arrival_time) VALUES(?,?,?,?,?)"
        );
        ps.setString(1, f.getFlightNumber());
        ps.setString(2, f.getOrigin());
        ps.setString(3, f.getDestination());
        ps.setTimestamp(4, Timestamp.valueOf(f.getDepartureTime()));
        ps.setTimestamp(5, Timestamp.valueOf(f.getArrivalTime()));
        ps.executeUpdate();
        ps.close();
    }

    public void update(Flight f) throws Exception {
        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "UPDATE flights SET flight_number=?,origin=?,destination=?,departure_time=?,arrival_time=? WHERE id=?"
        );
        ps.setString(1, f.getFlightNumber());
        ps.setString(2, f.getOrigin());
        ps.setString(3, f.getDestination());
        ps.setTimestamp(4, Timestamp.valueOf(f.getDepartureTime()));
        ps.setTimestamp(5, Timestamp.valueOf(f.getArrivalTime()));
        ps.setInt(6, f.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void delete(int id) throws Exception {
        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM flights WHERE id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
