package com.codecool.shop.user;

public class User {
    private int id;
    private String name;
    private String email;
    private String token_for_authentication;
    private final boolean sysadmin;

    public User(String name, String email, String token_for_authentication, boolean sysadmin) {
        this.name = name;
        this.email = email;
        this.token_for_authentication = token_for_authentication;
        this.sysadmin = sysadmin;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getToken_for_authentication() {
        return token_for_authentication;
    }

    public void setToken_for_authentication(String token_for_authentication) {
        this.token_for_authentication = token_for_authentication;
    }
}
