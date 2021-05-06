package com.codecool.shop.dao;

import javax.sql.DataSource;
import java.sql.SQLException;

import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.user.User;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;

public class ShopDatabaseManager {
    private UserDao userDao;

    public void setup() throws SQLException {
        System.out.println("setup");
        DataSource dataSource = connect();
        System.out.println(dataSource);
        userDao = new UserDaoJdbc(dataSource);

    }

    public void saveUser(User user){
        userDao.save(user);
    }

    public User getUser(String email){
        User user = userDao.get(email);
        return user;
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "codecoolshop";
        String user = "user";
        String password = "tocsa";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

}
