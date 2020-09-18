package com.jdbc;

import com.jdbc.dao.SearchOption;
import com.jdbc.dao.UserDao;
import com.jdbc.dao.factory.DBCPDataSourceFactory;
import com.jdbc.dao.factory.DBType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static DataSource dataSource = DBCPDataSourceFactory.getDataSource(DBType.POSTGRESQL);
    private static UserDao userDao = new UserDao(dataSource);

    public static void main(String[] args) {
//        userDao.findAll().forEach(System.out::println);
//        System.out.println(userDao.delete(userDao.find(SearchOption.BY_NAME, "Hippo")));
//        System.out.println(userDao.find(SearchOption.BY_ID, 6));
    }
}
