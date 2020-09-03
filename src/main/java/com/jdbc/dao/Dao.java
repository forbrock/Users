package com.jdbc.dao;

import com.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface Dao {
    boolean save(User userObject) throws SQLException;
    User find(SearchOption searchOption, Object criterion) throws SQLException;
    List<User> findAll() throws SQLException;
    boolean update(User userObject) throws SQLException;
    boolean delete(User userObject) throws SQLException;
}
