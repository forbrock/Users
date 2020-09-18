package com.jdbc.dao;

import com.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    boolean save(T o) throws SQLException;
    T find(SearchOption searchOption, Object criterion) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean update(T o) throws SQLException;
    boolean delete(int id) throws SQLException;
}
