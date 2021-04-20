package com.jdbc;

import com.jdbc.dao.SearchOption;
import com.jdbc.dao.UserDao;
import com.jdbc.dao.factory.DBCPDataSourceFactory;
import com.jdbc.dao.factory.DBType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        DataSource dataSource = DBCPDataSourceFactory.getDataSource(DBType.H2_INMEMORY_DB);
        UserDao userDao = new UserDao(dataSource);

        createTable(dataSource);
        setTableData(dataSource, formatter);
        userDao.findAll().forEach(System.out::println);

        userDao.find(SearchOption.BY_ID, "2'; DELETE FROM users where id like '%");
        System.out.println();
        userDao.findAll().forEach(System.out::println);

/*
        DataSource dataSource = DBCPDataSourceFactory.getDataSource(DBType.POSTGRESQL);
        UserDao userDao = new UserDao(dataSource);
        try {
//            userDao.findAll().forEach(System.out::println);
//            System.out.println(userDao.delete(userDao.find(SearchOption.BY_NAME, "Hippo")));
//            System.out.println(userDao.find(SearchOption.BY_ID, 3));
//            System.out.println(userDao.delete(44));
        } catch (NoSuchElementException e) {
            String message = "Database doesn't contain this entry";
            logger.info(message);
        }
*/
    }

    public static void createTable(DataSource dataSource) {
        String query = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "birth DATE NOT NULL," +
                "email VARCHAR(64) NOT NULL);";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Something went wrong", e);
        }
    }

    public static void setTableData(DataSource dataSource, DateTimeFormatter formatter) {
        String query = "INSERT INTO users (name, birth, email) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "Ivanka");
            statement.setObject(2, LocalDate.parse("30/09/1997", formatter));
            statement.setString(3, "ivanka@somemail.com");
            statement.addBatch();

            statement.setString(1, "Petrushka");
            statement.setObject(2, LocalDate.parse("23/12/1984", formatter));
            statement.setString(3, "petrushka@somemail.com");
            statement.addBatch();

            statement.setString(1, "Jorik");
            statement.setObject(2, LocalDate.parse("01/01/2000", formatter));
            statement.setString(3, "jorik@somemail.com");
            statement.addBatch();

            statement.executeBatch();
        } catch (SQLException e) {
            logger.error("Something went wrong", e);
        }
    }
}
