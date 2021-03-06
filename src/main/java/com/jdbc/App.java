package com.jdbc;

import com.jdbc.dao.SearchOption;
import com.jdbc.dao.UserDao;
import com.jdbc.dao.factory.DBCPDataSourceFactory;
import com.jdbc.dao.factory.DBType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.NoSuchElementException;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        DataSource dataSource = DBCPDataSourceFactory.getDataSource(DBType.POSTGRESQL);
        UserDao userDao = new UserDao(dataSource);
        try {
//            userDao.findAll().forEach(System.out::println);
//            System.out.println(userDao.delete(userDao.find(SearchOption.BY_NAME, "Hippo")));
//            System.out.println(userDao.find(SearchOption.BY_ID, 3));
//            System.out.println(userDao.delete(44));
        } catch (NoSuchElementException e) {
            String message = "Database doesn't contain this entry";
            logger.info(message);
        }
    }
}
