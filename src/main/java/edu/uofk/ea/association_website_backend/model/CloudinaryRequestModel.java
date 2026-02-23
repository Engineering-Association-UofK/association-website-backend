package edu.uofk.ea.association_website_backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CloudinaryRequestModel {
    
    @NotBlank(message = "API Key cannot be blank.")
    private String apiKey;

    @NotBlank(message = "Cloud Name cannot be blank.")
    private String cloudName;

    @NotBlank(message = "Upload Preset cannot be blank.")
    private String uploadPreset;

    @NotBlank(message = "Resource Type cannot be blank.")
    private String resourceType;

    @NotNull(message = "Timestamp cannot be null.")
    private Long timestamp;

    private String uploadSignature;

    public String getCloudName() {
        return cloudName;
    }

    public void setCloudName(String cloudName) {
        this.cloudName = cloudName;
    }

    public String getUploadPreset() {
        return uploadPreset;
    }

    public void setUploadPreset(String uploadPreset) {
        this.uploadPreset = uploadPreset;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUploadSignature() {
        return uploadSignature;
    }

    public void setUploadSignature(String uploadSignature) {
        this.uploadSignature = uploadSignature;
    }
}
