package edu.uofk.ea.association_website_backend.model.admin;

import io.jsonwebtoken.security.Password;

public class LoginRequest {
    private String name;
    private String Password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
