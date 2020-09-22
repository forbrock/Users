package com.jdbc.dao.factory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBCPDataSourceFactory {
    private static final BasicDataSource basicDataSource = new BasicDataSource();
    private static final Logger logger = LoggerFactory.getLogger(DBCPDataSourceFactory.class);

    public static BasicDataSource getDataSource(DBType type) {
        basicDataSource.setMinIdle(1);
        basicDataSource.setMaxIdle(3);
        basicDataSource.setMaxOpenPreparedStatements(30);

        switch (type) {
            case POSTGRESQL:
                initPostgreSQL();
                break;
            case H2_INMEMORY_DB:
                initH2InMemoryDb();
                break;
            default:
                logger.error("Wrong DBType passed: '{}'", type);
                throw new IllegalArgumentException("Wrong DBType passed");
        }
        return basicDataSource;
    }

    private static Properties getProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (IOException e) {
            logger.error(String.format("Something went wrong: file \"%s\"", path), e);
        }
        return properties;
    }

    private static void initPostgreSQL() {
        String propertiesPath = "src/main/resources/postgresql.properties";
        Properties properties = getProperties(propertiesPath);

        String url = String.format("%s://%s:%s/%s",
                properties.getProperty("dbPrefix"),
                properties.getProperty("serverName"),
                properties.getProperty("port"),
                properties.getProperty("database"));

        basicDataSource.setUrl(url);
        basicDataSource.setUsername(properties.getProperty("user"));
        basicDataSource.setPassword(properties.getProperty("password"));
    }

    private static void initH2InMemoryDb() {
        String propertiesPath = "src/test/resources/in-memorydb.properties";
        Properties properties = getProperties(propertiesPath);

        String url = String.format("%s%s;%s",
                properties.getProperty("dbPrefix"),
                properties.getProperty("database"),
                properties.getProperty("dbCloseDelay"));

        basicDataSource.setUrl(url);
    }
}
