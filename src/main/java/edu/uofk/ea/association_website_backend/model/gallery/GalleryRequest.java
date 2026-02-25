package edu.uofk.ea.association_website_backend.model.gallery;

import jakarta.validation.constraints.NotBlank;

public class GalleryRequest {
    @NotBlank
    private String publicId;
    @NotBlank
    private String url;

    public GalleryRequest() {
    }

    public GalleryRequest(String publicId, String url) {
        this.publicId = publicId;
        this.url = url;
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
}
