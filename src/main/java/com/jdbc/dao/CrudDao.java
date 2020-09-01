package com.jdbc.dao;

import com.jdbc.model.User;

public interface CrudDao {
    void addUser(User user);
    void findUser(String query);
    void updateUser(int id, User newUser);
    void removeUser(int id);
}
