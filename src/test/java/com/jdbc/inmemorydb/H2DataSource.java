package com.jdbc.inmemorydb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DataSource {
    private static final String URL = "jdbc:h2:mem:dbtest;DB_CLOSE_DELAY=-1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
