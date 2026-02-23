package edu.uofk.ea.association_website_backend.model.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotBlank(message = "Username is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters.")
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 40, message = "Password must be at least 8 characters long, and must not exceed 40 characters.")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
