package edu.uofk.ea.association_website_backend.model.activity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class DailyActivityId implements Serializable {
    private int adminId;
    private LocalDate date;

    public DailyActivityId() {
    }

    public DailyActivityId(int adminId, LocalDate date) {
        this.adminId = adminId;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyActivityId that = (DailyActivityId) o;
        return adminId == that.adminId && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, date);
    }
}
