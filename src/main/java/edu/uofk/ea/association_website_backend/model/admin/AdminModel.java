package edu.uofk.ea.association_website_backend.model.admin;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "admins")
public class AdminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "verified")
    private boolean isVerified;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AdminStatus status;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AdminRole role;

    @Column(name = "created_at")
    private Instant createdAt;

    public AdminModel() {}

    public AdminModel(int id, String name, String email, String password, AdminRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

        this.isVerified = false;
        this.status = AdminStatus.active;
        this.createdAt = Instant.now();
    }

    public AdminModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public AdminModel(String name, String email, String password, AdminRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean verified) {
        isVerified = verified;
    }

    public AdminStatus getStatus() {
        return status;
    }

    public void setStatus(AdminStatus status) {
        this.status = status;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AdminModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isVerified=" + isVerified +
                ", status=" + status +
                ", role=" + role +
                ", createdAt=" + createdAt +
                '}';
    }
}
