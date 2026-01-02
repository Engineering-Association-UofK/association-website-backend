package edu.uofk.ea.association_website_backend.model;

public class CloudinaryRequestModel {

    private String apiKey;
    private String cloudName;
    private String uploadPreset;
    private String resourceType;
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
