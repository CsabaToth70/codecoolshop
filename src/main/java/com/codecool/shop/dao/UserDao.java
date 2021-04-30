package com.codecool.shop.dao;

import com.codecool.shop.user.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserDao {

    void save(User user);
    Set<User> getAll();
    void modifyEmail(User user);
    Optional<User> get(UUID id);
    void update(User user, String[] params);
    void delete(User user);

}
