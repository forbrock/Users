package com.jdbc.dao.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBCPDataSourceFactory {
    private static final Logger logger = LoggerFactory.getLogger(DBCPDataSourceFactory.class);

    public static DataSource getDBCPObject(DBType type) {
        if (type == null) {
            logger.error("No arguments passed, 'DBType = null'");
            throw new IllegalArgumentException("No arguments passed: " + null);
        }

        switch (type) {
            case POSTGRESQL: return new PostgresDataSource();
            case H2_INMEMORY_DB: return new H2DataSource();
        }
        logger.error("Wrong DBType passed: '{}'", type);
        return null;
    }
}
