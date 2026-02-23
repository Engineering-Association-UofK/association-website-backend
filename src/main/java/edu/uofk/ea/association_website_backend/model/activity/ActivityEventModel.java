package edu.uofk.ea.association_website_backend.model.activity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name="activity_events")
public class ActivityEventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private ActivityType eventType;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "meta_data")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metaData;

    public ActivityEventModel() {
    }

    public ActivityEventModel(ActivityType eventType, int adminId, Map<String, Object> metaData) {
        this.eventType = eventType;
        this.adminId = adminId;
        this.metaData = metaData;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
