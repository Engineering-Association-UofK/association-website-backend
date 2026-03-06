package edu.uofk.ea.association_website_backend.model.blog;

import edu.uofk.ea.association_website_backend.model.gallery.GalleryRequest;

import java.time.Instant;

public class BlogPostResponse {
    private int id;
    private String authorName;
    private String title;
    private String content;
    private GalleryRequest image;
    private Instant createdAt;
    private Instant updatedAt;
    private BlogPostModel.Status status;

    public BlogPostResponse(int id, String title, String content, GalleryRequest image, String authorName, Instant createdIt, Instant updatedAt, BlogPostModel.Status status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.authorName = authorName;
        this.createdAt = createdIt;
        this.updatedAt = updatedAt;
        this.status = status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GalleryRequest getImage() {
        return image;
    }

    public void setImage(GalleryRequest image) {
        this.image = image;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public BlogPostModel.Status getStatus() {
        return status;
    }

    public void setStatus(BlogPostModel.Status status) {
        this.status = status;
    }
}
