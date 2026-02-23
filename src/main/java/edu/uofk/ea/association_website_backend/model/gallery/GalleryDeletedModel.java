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

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private GalleryItemType type;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "created_at")
    private Instant createdAt;

    public GalleryDeletedModel() {}

    public GalleryDeletedModel(String imageLink, GalleryItemType type, String keyword) {
        this.imageLink = imageLink;
        this.type = type;
        this.keyword = keyword;
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

    public GalleryItemType getType() {
        return type;
    }

    public void setType(GalleryItemType type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }


}
