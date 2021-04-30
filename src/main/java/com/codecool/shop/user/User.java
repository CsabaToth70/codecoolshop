package com.codecool.shop.user;

import javax.swing.*;
import java.util.UUID;

public class User {
    private UUID user_id = UUID.randomUUID();
    private String name;
    private String email;
    private JPasswordField passwordField = new JPasswordField(10);

    public User(String name, String email, JPasswordField passwordField) {
        this.name = name;
        this.email = email;
        this.passwordField = passwordField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public UUID getUser_id() {
        return user_id;
    }
}
