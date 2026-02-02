package edu.uofk.ea.association_website_backend.model.admin;

import jakarta.persistence.*;

@Entity
@Table(name="old_admins")
public class OldAdminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private java.time.Instant createdAt;

    public OldAdminModel() {}

    public OldAdminModel(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = java.time.Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public java.time.Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.Instant createdAt) {
        this.createdAt = createdAt;
    }

}
