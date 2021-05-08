package com.codecool.shop.dao;

import com.codecool.shop.user.User;


public interface UserDao {
    void save(User user);
    User get(String email);
}
