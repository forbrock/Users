package com.jdbc.dao.factory;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class H2DataSource implements DataSource {
    String path = "src/test/resources/in-memorydb.properties";
    private BasicDataSource ds = new BasicDataSource();

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public H2DataSource() {
        Properties properties = getProperties(path);
        String url = String.format("%s%s;%s",
                properties.getProperty("dbPrefix"),
                properties.getProperty("database"),
                properties.getProperty("dbCloseDelay"));

        ds.setUrl(url);
        ds.setMinIdle(1);
        ds.setMaxIdle(3);
        ds.setMaxOpenPreparedStatements(30);
    }
}
