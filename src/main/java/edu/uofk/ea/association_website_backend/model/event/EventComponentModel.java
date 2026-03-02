package edu.uofk.ea.association_website_backend.model.event;

import jakarta.persistence.*;

@Entity
@Table(name = "event_components")
public class EventComponentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "event_id")
    private int eventId;

    @Column(name = "name")
    private String name;

    @Column(name = "max_score")
    private double maxScore;

    public EventComponentModel() {}

    public EventComponentModel(int eventId, String name, double maxScore) {
        this.eventId = eventId;
        this.name = name;
        this.maxScore = maxScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
