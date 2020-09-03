package com.jdbc.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:postgresql://localhost:5432/users_db");
        ds.setUsername("study");
        ds.setPassword("study");
        ds.setMinIdle(1);
        ds.setMaxIdle(3);
        ds.setMaxOpenPreparedStatements(30);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource() { }
}
