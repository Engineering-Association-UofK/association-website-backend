package edu.uofk.ea.association_website_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "verification_codes")
public class VerificationCodeModel {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "code")
    private String code;

    @Column(name = "created_at")
    private Instant createdAt;

    public VerificationCodeModel() {}

    public VerificationCodeModel(int adminId, String code){
        this.adminId = adminId;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
