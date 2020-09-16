package com.jdbc.dao.factory;

public class DBCPDataSourceFactory {

    public DataSource getDBCPObject(DBType type) {
        if (type == null) throw new IllegalArgumentException("Wrong doughnut type: " + null);

        switch (type) {
            case POSTGRESQL: return new PostgresDataSource();
            case H2_INMEMORY_DB: return new H2DataSource();
        }
        return null;
    }
}
