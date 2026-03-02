package edu.uofk.ea.association_website_backend.model.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventComponentRequest {
    private Integer id;
    @NotNull(message = "Event ID is required")
    private int eventId;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Max score is required")
    private double maxScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }
}
