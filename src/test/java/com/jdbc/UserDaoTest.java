package com.jdbc;

import com.jdbc.dao.SearchOption;
import com.jdbc.dao.UserDao;
import com.jdbc.dao.factory.DBType;
import com.jdbc.model.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private static UserDao userDao = new UserDao(DBType.H2_INMEMORY_DB);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    @BeforeEach
    public void dropTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Connection connection = userDao.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void createTable() {
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

    @BeforeEach
    public void setTableData() {
        String query = "INSERT INTO users (name, birth, email) VALUES (?, ?, ?)";
        try (Connection connection = userDao.getConnection();
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
            e.printStackTrace();
        }
    }

    @Test
    public void findByIdTest() {
        userDao.findAll().forEach(System.out::println);
        User expectedUser = new User(3,"Jorik",
                LocalDate.parse("01/01/2000", formatter), "jorik@somemail.com");
        User actualUser = userDao.find(SearchOption.BY_ID, expectedUser.getId());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByNameTest() {
        User expectedUser = new User(3,"Jorik",
                LocalDate.parse("01/01/2000", formatter), "jorik@somemail.com");
        User actualUser = userDao.find(SearchOption.BY_NAME, expectedUser.getName());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByDateTest() {
        User expectedUser = new User(3,"Jorik",
                LocalDate.parse("01/01/2000", formatter), "jorik@somemail.com");
        User actualUser = userDao.find(SearchOption.BY_BIRTH, expectedUser.getBirth());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByEmailTest() {
        User expectedUser = new User(3,"Jorik",
                LocalDate.parse("01/01/2000", formatter), "jorik@somemail.com");
        User actualUser = userDao.find(SearchOption.BY_EMAIL, expectedUser.getEmail());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findAllTest() {
        List<User> expectedUsers = new ArrayList<>();
        List<User> actualUsers = userDao.findAll();
        expectedUsers.add(new User(1,"Ivanka",
                LocalDate.parse("30/09/1997", formatter), "ivanka@somemail.com"));
        expectedUsers.add(new User(3,"Petrushka",
                LocalDate.parse("23/12/1984", formatter), "petrushka@somemail.com"));
        expectedUsers.add(new User(3,"Jorik",
                LocalDate.parse("01/01/2000", formatter), "jorik@somemail.com"));

        assertIterableEquals(expectedUsers, actualUsers);
    }

    @Test
    public void saveTest() {
        User expectedUser = new User(4, "Hippo",
                LocalDate.parse("27/03/1999", formatter), "hippo@somemail.com");
        userDao.save(expectedUser);
        User actualUser = userDao.find(SearchOption.BY_ID, 4);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void updateTest() {
        User expectedUser = new User(1, "Ivanka",
                LocalDate.parse("30/09/1997", formatter), "ivanka@somemail.com");
        userDao.update(expectedUser);
        User actualUser = userDao.find(SearchOption.BY_ID, expectedUser.getId());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void deleteTest() {
        User expectedUser = new User(1, "Ivanka",
                LocalDate.parse("30/09/1997", formatter), "ivanka@somemail.com");
        userDao.delete(expectedUser);
        User actualUser = userDao.find(SearchOption.BY_ID, expectedUser.getId());
        assertNotEquals(expectedUser, actualUser);
    }
}
