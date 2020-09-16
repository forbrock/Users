package com.jdbc;

import com.jdbc.dao.SearchOption;
import com.jdbc.dao.UserDao;
import com.jdbc.dao.factory.DBType;
import com.jdbc.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private static UserDao userDao = new UserDao(DBType.H2_INMEMORY_DB);
    private User expectedUser = new User("Hippo", "27/03/1999", "hippo@somemail.com");

    @BeforeAll
    public static void createTable() {
        String query = "CREATE TABLE users (" +
                "id SERIAL," +
                "name VARCHAR(100) NOT NULL," +
                "birth DATE NOT NULL," +
                "email VARCHAR(64) NOT NULL," +
                "PRIMARY KEY (id));";
        try (Connection connection = userDao.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void setTableData() {
        String query = "INSERT INTO users (name, birth, email) VALUES (?, ?, ?)";
        try (Connection connection = userDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

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
            e.printStackTrace();
        }
    }

    @Test
    public void saveTest() {
        userDao.save(expectedUser);
        String query = String.format("SELECT * FROM users WHERE name = '%s'", expectedUser.getName());
        User actualUser = null;
        try (Connection conn = userDao.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate birth = resultSet.getObject("birth", LocalDate.class);
                String email = resultSet.getString("email");

                actualUser = new User(id, name, birth, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(expectedUser, actualUser);
    }

    @Test
    @Disabled
    public void findByIdTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        User expectedUser = new User(3,"Jorik",
                LocalDate.parse("01/01/2000", formatter), "jorik@somemail.com");
        User actualUser = userDao.find(SearchOption.BY_ID, 3);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    @Disabled
    public void findByNameTest() {

    }

    @Test
    @Disabled
    public void findByDateTest() {

    }

    @Test
    @Disabled
    public void findByEmailTest() {

    }

    @Test
    public void findAllTest() {
        List<User> actualUsers = userDao.findAll();
        List<User> expectedUsers = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = userDao.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate birth = resultSet.getObject("birth", LocalDate.class);
                String email = resultSet.getString("email");

                expectedUsers.add(new User(id, name, birth, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertIterableEquals(expectedUsers, actualUsers);
    }

    @Test
    @Disabled
    public void updateTest() {

    }

    @Test
    @Disabled
    public void deleteTest() {

    }
}
