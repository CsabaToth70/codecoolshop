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
            String sql = "INSERT INTO users (name, email, token_for_authentication, sysadmin) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getToken_for_authentication());
            statement.setBoolean(4, user.isSysadmin());
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
    public User get(String email) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "SELECT id, name, email, token_for_authentication, sysadmin FROM users WHERE email = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if(!rs.next()){
                return null;
            }
            int userId = rs.getInt(1);
            String name = rs.getString(2);
            String token_for_authentication = rs.getString(4);
            boolean sysadmin = rs.getBoolean(5);
            User user = new User(name, email, token_for_authentication, sysadmin);
            user.setId(userId);
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
