package com.jdbc.dao;

import com.jdbc.dao.factory.DBCPDataSourceFactory;
import com.jdbc.dao.factory.DBType;
import com.jdbc.dao.factory.DataSource;
import com.jdbc.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao {
    DataSource dataSource;

    public UserDao(DBType type) {
        DBCPDataSourceFactory dbcpFactory = new DBCPDataSourceFactory();
        dataSource = dbcpFactory.getDBCPObject(type);
    }

    private User queryExecuteHelper(String query) {
        User user = null;
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            conn.setReadOnly(true);

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate birth = resultSet.getObject("birth", LocalDate.class);
                String email = resultSet.getString("email");

                user = new User(id, name, birth, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public boolean save(User userObject) {
        boolean rowInserted = false;
        String query = "INSERT INTO users (name, birth, email) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, userObject.getName());
            statement.setObject(2, userObject.getBirth());
            statement.setString(3, userObject.getEmail());

            rowInserted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

    @Override
    public User find(SearchOption searchOption, Object criterion) {
        User user = null;
        String queryParameter = "";
        String query = "";
        switch (searchOption) {
            case BY_ID:
                queryParameter = "id";
                query = String.format("SELECT * FROM users WHERE %s = %s",
                        queryParameter, String.valueOf(criterion));
                user = queryExecuteHelper(query);
                break;
            case BY_NAME:
                queryParameter = "name";
                query = String.format("SELECT * FROM users WHERE %s = '%s'",
                        queryParameter, String.valueOf(criterion));
                user = queryExecuteHelper(query);
                break;
            case BY_BIRTH:
                queryParameter = "birth";
                query = String.format("SELECT * FROM users WHERE %s = '%s'",
                        queryParameter, String.valueOf(criterion));
                user = queryExecuteHelper(query);
                break;
            case BY_EMAIL:
                queryParameter = "email";
                query = String.format("SELECT * FROM users WHERE %s = '%s'",
                        queryParameter, String.valueOf(criterion));
                user = queryExecuteHelper(query);
                break;
            default:
                System.out.println("Wrong option or criterion");
                break;
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String query = "SELECT id, name, birth, email FROM users";
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            conn.setReadOnly(true);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate birth = resultSet.getObject("birth", LocalDate.class);
                String email = resultSet.getString("email");

                User user = new User(id, name, birth, email);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean update(User userObject) {
        boolean rowUpdated = false;
        String query = "UPDATE users SET name = ?, birth = ?, email = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, userObject.getName());
            statement.setObject(2, userObject.getBirth());
            statement.setString(3, userObject.getEmail());
            statement.setInt(4, userObject.getId());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public boolean delete(User userObject) {
        boolean rowDeleted = false;
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userObject.getId());
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return false;
        }
        return rowDeleted;
    }
}
