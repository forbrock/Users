package com.jdbc.dao.factory;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresDataSource implements DataSource {
    String path = "src/main/resources/postgresql.properties";
    private BasicDataSource ds = new BasicDataSource();

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public PostgresDataSource() {
        Properties properties = getProperties(path);

        String url = String.format("%s://%s:%s/%s",
                properties.getProperty("dbPrefix"),
                properties.getProperty("serverName"),
                properties.getProperty("port"),
                properties.getProperty("database"));

        ds.setUrl(url);
        ds.setUsername(properties.getProperty("user"));
        ds.setPassword(properties.getProperty("password"));
        ds.setMinIdle(1);
        ds.setMaxIdle(3);
        ds.setMaxOpenPreparedStatements(30);
    }
}
