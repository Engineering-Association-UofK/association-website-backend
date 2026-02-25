package edu.uofk.ea.association_website_backend.model.gallery;

import java.time.Instant;

public class GalleryResponse {
    private int id;
    private String url;
    private Instant createdAt;
    private boolean isNews;

    public GalleryResponse() {
    }

    public GalleryResponse(int id, String url, Instant createdAt, boolean isNews) {
        this.id = id;
        this.url = url;
        this.createdAt = createdAt;
        this.isNews = isNews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isNews() {
        return isNews;
    }

    public void setNews(boolean news) {
        isNews = news;
    }
}
