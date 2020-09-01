package com.jdbc;

import com.jdbc.dao.UserDao;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        Connection connection = userDao.getConnection();

        userDao.findUser("select * from users");
        userDao.getUsers().forEach(System.out::println);

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
