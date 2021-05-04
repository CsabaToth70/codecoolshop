package com.codecool.shop.user;

public class User {
    private String name;
    private String email;
    private String token_for_authentication;

    public User(String name, String email, String token_for_authentication) {
        this.name = name;
        this.email = email;
        this.token_for_authentication = token_for_authentication;
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

    public String getToken_for_authentication() {
        return token_for_authentication;
    }

    public void setToken_for_authentication(String token_for_authentication) {
        this.token_for_authentication = token_for_authentication;
    }
}
