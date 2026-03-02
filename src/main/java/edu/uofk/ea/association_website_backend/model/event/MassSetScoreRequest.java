package edu.uofk.ea.association_website_backend.model.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class MassSetScoreRequest {
    @NotNull(message = "Event ID is required")
    private Integer eventId;
    @NotNull(message = "Component ID is required")
    private Integer componentId;
    @NotNull(message = "Student scores are required")
    private Map<
            @NotBlank(message = "Student ID is required") String,
            @NotNull(message = "Score is required") Double
            > studentScores;

    public MassSetScoreRequest() {
    }

    public MassSetScoreRequest(int eventId, int componentId, Map<String, Double> studentScores) {
        this.eventId = eventId;
        this.componentId = componentId;
        this.studentScores = studentScores;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public Map<String, Double> getStudentScores() {
        return studentScores;
    }

    public void setStudentScores(Map<String, Double> studentScores) {
        this.studentScores = studentScores;
    }
}
