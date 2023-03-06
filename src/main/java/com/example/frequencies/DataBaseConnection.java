package com.example.frequencies;

import java.sql.*;

public class DataBaseConnection {
    public Connection connection;
    public Connection getConnection() {
        String url = "jdbc:mysql://localhost/frequencies";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
