package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.user.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UserDaoJdbc implements UserDao {
    private static UserDaoJdbc instance = null;

    public UserDaoJdbc() {
    }

    public static UserDaoJdbc getInstance(){
        if (instance == null) {
            instance = new UserDaoJdbc();
        }
        return instance;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public Set<User> getAll() {
        return null;
    }

    @Override
    public void modifyEmail(User user) {

    }

    @Override
    public Optional<User> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
