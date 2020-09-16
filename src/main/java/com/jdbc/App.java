package com.jdbc;

import com.jdbc.dao.SearchOption;
import com.jdbc.dao.UserDao;
import com.jdbc.dao.factory.DBType;

public class App {
    public static void main(String[] args) {
        UserDao userDao = new UserDao(DBType.POSTGRESQL);
//        userDao.findAll().forEach(System.out::println);
//        System.out.println(userDao.delete(userDao.find(SearchOption.BY_NAME, "Hippo")));
        System.out.println(userDao.find(SearchOption.BY_ID, 6));
    }
}
