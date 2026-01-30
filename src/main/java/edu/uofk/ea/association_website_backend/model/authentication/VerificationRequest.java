package edu.uofk.ea.association_website_backend.model.authentication;

import jakarta.validation.constraints.NotBlank;

public class VerificationRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}