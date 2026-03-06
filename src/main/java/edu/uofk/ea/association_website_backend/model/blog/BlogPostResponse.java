package edu.uofk.ea.association_website_backend.model.blog;

import edu.uofk.ea.association_website_backend.model.gallery.GalleryRequest;

public class BlogPostResponse {
    private int id;
    private int authorId;
    private String title;
    private String content;
    private GalleryRequest image;
    private BlogPostModel.Status status;

    public BlogPostResponse(int id, String title, String content, GalleryRequest image, int authorId, BlogPostModel.Status status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.authorId = authorId;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public BlogPostModel.Status getStatus() {
        return status;
    }

    public void setStatus(BlogPostModel.Status status) {
        this.status = status;
    }
}
