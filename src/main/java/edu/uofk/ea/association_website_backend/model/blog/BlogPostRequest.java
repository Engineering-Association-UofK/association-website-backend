package edu.uofk.ea.association_website_backend.model.blog;

import edu.uofk.ea.association_website_backend.model.gallery.GalleryRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BlogPostRequest {

    private int id;

    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 250, message = "Title cannot exceed 255 characters.")
    private String title;

    @NotBlank(message = "Content cannot be blank.")
    private String content;

    private GalleryRequest image;

    @NotNull(message = "Status cannot be null.")
    private BlogPostModel.Status status;

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

    public BlogPostModel.Status getStatus() {
        return status;
    }

    public void setStatus(BlogPostModel.Status status) {
        this.status = status;
    }
}