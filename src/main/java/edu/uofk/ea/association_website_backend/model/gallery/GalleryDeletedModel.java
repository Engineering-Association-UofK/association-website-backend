package edu.uofk.ea.association_website_backend.model.gallery;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="deleted_gallery")
public class GalleryDeletedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "created_at")
    private Instant createdAt;

    public GalleryDeletedModel() {}

    public GalleryDeletedModel(String imageLink) {
        this.imageLink = imageLink;
        this.createdAt = Instant.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }


}
