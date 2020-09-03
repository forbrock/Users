package com.jdbc.dao;

import com.jdbc.model.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements CrudDao {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/users_db";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private static Connection connection;
    private List<User> users;

    public UserDao() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            if (connection != null) {
                System.out.println("Connected to user_db");
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect");
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void addUser(User user) {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("INSERT INTO users (name, birth, email) VALUES ('%s', '%s', '%s')",
                    user.getName(), new SimpleDateFormat("d/MM/yyyy").format(user.getBirth()), user.getEmail());
            int insertedRowsNumber = statement.executeUpdate(query);
            if (insertedRowsNumber > 0) {
                System.out.println("The entry added successfully");
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findUser(String query) {
        if (users == null) {
            users = new ArrayList<>();
        }
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setBirth(resultSet.getDate("birth"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(int id, User newUser) {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("UPDATE users SET name='%s', birth='%s', email='%s' WHERE id=%d",
                            newUser.getName(), newUser.getBirth(), newUser.getEmail(), id);
            int isUpdated = statement.executeUpdate(query);
            if (isUpdated > 0) {
                System.out.println("Raw updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(int id) {

    }
}
