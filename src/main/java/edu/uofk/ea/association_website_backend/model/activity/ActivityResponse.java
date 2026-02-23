package edu.uofk.ea.association_website_backend.model.activity;

import java.time.Instant;
import java.util.Map;

public class ActivityResponse {
    private ActivityType eventType;
    private Instant createdAt;
    private Map<String, Object> metaData;

    public ActivityResponse(ActivityType eventType, Instant createdAt, Map<String, Object> metaData) {
        this.eventType = eventType;
        this.createdAt = createdAt;
        this.metaData = metaData;
    }

    public ActivityType getEventType() {
        return eventType;
    }

    public void setEventType(ActivityType eventType) {
        this.eventType = eventType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
    }
}
