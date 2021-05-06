package com.codecool.shop.dao;

import com.codecool.shop.user.User;

import java.util.Optional;
import java.util.Set;

public interface UserDao {

    void save(User user);
    Set<User> getAll();
    void modifyEmail(User user);
    User get(String email);
    void update(User user, String[] params);
    void delete(User user);

}
