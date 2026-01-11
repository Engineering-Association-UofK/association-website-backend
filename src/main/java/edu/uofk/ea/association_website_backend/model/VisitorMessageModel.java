package edu.uofk.ea.association_website_backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "visitors_messages")
public class VisitorMessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private Instant createdAt;

    public VisitorMessageModel(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}