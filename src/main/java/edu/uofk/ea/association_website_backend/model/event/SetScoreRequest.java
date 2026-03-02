package edu.uofk.ea.association_website_backend.model.event;

import jakarta.validation.constraints.NotNull;

public class SetScoreRequest {
    @NotNull(message = "Event ID is required")
    private int eventId;
    @NotNull(message = "Student ID is required")
    private int studentId;
    @NotNull(message = "Component ID is required")
    private int componentId;
    @NotNull(message = "Score is required")
    private double score;

    public SetScoreRequest() {
    }

    public SetScoreRequest(int eventId, int studentId, int componentId, double score) {
        this.eventId = eventId;
        this.studentId = studentId;
        this.componentId = componentId;
        this.score = score;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
