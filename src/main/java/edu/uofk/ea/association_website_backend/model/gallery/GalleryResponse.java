package edu.uofk.ea.association_website_backend.model.gallery;

import java.time.Instant;

public class GalleryResponse {
    private int id;
    private GalleryRequest image;
    private Instant createdAt;
    private boolean isNews;

    public GalleryResponse() {
    }

    public GalleryResponse(int id, GalleryRequest image, Instant createdAt, boolean isNews) {
        this.id = id;
        this.image = image;
        this.createdAt = createdAt;
        this.isNews = isNews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GalleryRequest getImage() {
        return image;
    }

    public void setImage(GalleryRequest image) {
        this.image = image;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isNews() {
        return isNews;
    }

    public void setNews(boolean news) {
        isNews = news;
    }
}
