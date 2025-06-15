package com.example.airlinereservation.dao;

import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static Connection conn;

    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            Properties props = new Properties();
            props.load(DBConnection.class.getClassLoader()
                .getResourceAsStream("config.properties"));
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");
            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }
}
