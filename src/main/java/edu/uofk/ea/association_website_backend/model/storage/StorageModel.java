package edu.uofk.ea.association_website_backend.model.storage;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "storage")
public class StorageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StoreType type;

    @Column(name = "public_id", unique = true)
    private String publicId;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "reference_num", nullable = false)
    private int referenceNum;

    public StorageModel() {
    }

    public StorageModel(StoreType type, String publicId, String url) {
        this.type = type;
        this.publicId = publicId;
        this.url = url;
        this.referenceNum = 0;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StoreType getType() {
        return type;
    }

    public void setType(StoreType type) {
        this.type = type;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getReferenceNum() {
        return referenceNum;
    }

    public void setReferenceNum(int referenceNum) {
        this.referenceNum = referenceNum;
    }
}
