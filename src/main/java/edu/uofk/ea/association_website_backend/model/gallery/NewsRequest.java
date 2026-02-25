package edu.uofk.ea.association_website_backend.model.gallery;

import jakarta.validation.constraints.NotBlank;

public class NewsRequest {
    @NotBlank
    private int storageId;
    @NotBlank
    private String alt;

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}