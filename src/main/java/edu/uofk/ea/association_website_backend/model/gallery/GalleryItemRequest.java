package edu.uofk.ea.association_website_backend.model.gallery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GalleryItemRequest {

    private int id;

    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 255, message = "Title cannot exceed 255 characters.")
    private String title;

    @NotNull(message = "Type cannot be null.")
    private GalleryItemType type;

    @Size(min = 3, max = 50, message = "Keyword must be at least 3 characters long, and must not exceed 50 characters.")
    private String keyword;

    @NotBlank(message = "Image link cannot be blank.")
    private String imageLink;

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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
