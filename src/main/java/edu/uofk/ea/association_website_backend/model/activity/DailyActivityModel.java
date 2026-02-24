package edu.uofk.ea.association_website_backend.model.activity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="daily_activity")
@IdClass(DailyActivityId.class)
public class DailyActivityModel {

    @Id
    @Column(name = "admin_id")
    private int adminId;

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "activity_count")
    private int activityCount;

    public DailyActivityModel() {
    }

    public DailyActivityModel(int adminId, LocalDate date) {
        this.adminId = adminId;
        this.date = date;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }
}
