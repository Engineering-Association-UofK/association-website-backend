package edu.uofk.ea.association_website_backend.model.gallery;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="gallery")
public class GalleryItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private GalleryItemType type;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "created_at")
    private Instant createdAt;

    public GalleryItemModel(){}

    public GalleryItemModel(String title, GalleryItemType type){
        this.title = title;
        this.type = type;
    }

    public GalleryItemModel(String title, GalleryItemType type, String keyword){
        this.title = title;
        this.type = type;
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + type + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
