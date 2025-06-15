package com.example.airlinereservation.dao;

import com.example.airlinereservation.models.VIPPassenger;
import com.example.airlinereservation.models.Passenger;
import java.sql.*;
import java.util.*;

public class PassengerDAO {

    public List<Passenger> getAll() throws Exception {
        Connection c = DBConnection.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM passengers");
        List<Passenger> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String fn = rs.getString("first_name");
            String ln = rs.getString("last_name");
            String pp = rs.getString("passport");
            String ff = rs.getString("frequent_flyer_number");
            int miles = rs.getInt("miles");
            String vip = rs.getString("vip_level");
            //  jeśli vip_level != null => VIPPassenger
            if (vip != null && !vip.isEmpty()) {
                list.add(new VIPPassenger(id, fn, ln, pp, ff, miles, vip));
            } else {
                list.add(new Passenger(id, fn, ln, pp));
            }
        }
        rs.close(); st.close();
        return list;
    }

    public void insert(Passenger p) throws Exception {
        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "INSERT INTO passengers(first_name,last_name,passport,frequent_flyer_number,miles,vip_level) VALUES(?,?,?,?,?,?)");
        ps.setString(1, p.getFirstName());
        ps.setString(2, p.getLastName());
        ps.setString(3, p.getPassport());
        if (p instanceof VIPPassenger) {
            VIPPassenger vp = (VIPPassenger)p;
            ps.setString(4, vp.getFrequentFlyerNumber());
            ps.setInt(5, vp.getMiles());
            ps.setString(6, vp.getVipLevel());
        } else {
            ps.setString(4, null);
            ps.setInt(5, 0);
            ps.setString(6, null);
        }
        ps.executeUpdate();
        ps.close();
    }

    public void update(Passenger p) throws Exception {
        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "UPDATE passengers SET first_name=?,last_name=?,passport=?,frequent_flyer_number=?,miles=?,vip_level=? WHERE id=?");
        ps.setString(1, p.getFirstName());
        ps.setString(2, p.getLastName());
        ps.setString(3, p.getPassport());
        if (p instanceof VIPPassenger) {
            VIPPassenger vp = (VIPPassenger)p;
            ps.setString(4, vp.getFrequentFlyerNumber());
            ps.setInt(5, vp.getMiles());
            ps.setString(6, vp.getVipLevel());
        } else {
            ps.setString(4, null);
            ps.setInt(5, 0);
            ps.setString(6, null);
        }
        ps.setInt(7, p.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void delete(int id) throws Exception {
        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM passengers WHERE id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
