package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.user.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;
import java.util.Set;

public class UserDaoJdbc implements UserDao {
    private static UserDaoJdbc instance = null;
    private DataSource dataSource;

    public UserDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void save(User user) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO users (name, email, token_for_authentication) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getToken_for_authentication());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<User> getAll() {
        return null;
    }

    @Override
    public void modifyEmail(User user) {

    }

    @Override
    public Optional<User> get(String email) {
        return Optional.empty();
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
