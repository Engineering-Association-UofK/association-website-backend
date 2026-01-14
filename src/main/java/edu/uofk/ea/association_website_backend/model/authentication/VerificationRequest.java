package edu.uofk.ea.association_website_backend.model.authentication;

public class VerificationRequest {
    private String name;
    private String code;

    public VerificationRequest() {}

    public VerificationRequest(String name, String code) {
        this.name = name;
        this.code = code;
    }

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