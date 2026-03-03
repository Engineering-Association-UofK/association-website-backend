package edu.uofk.ea.association_website_backend.model.event;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "events")
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "max_participants")
    private int maxParticipants;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ElementCollection
    @CollectionTable(name = "event_outcomes", joinColumns = @JoinColumn(name = "event_id"))
    private List<String> outcomes;

    public EventModel() {}

    public EventModel(String name, EventType eventType, int maxParticipants, LocalDate startDate, LocalDate endDate, List<String> outcomes) {
        this.name = name;
        this.eventType = eventType;
        this.maxParticipants = maxParticipants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.outcomes = outcomes;
    }

    public EventModel(String name, EventType eventType, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.eventType = eventType;
        this.maxParticipants = 0;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<String> outcomes) {
        this.outcomes = outcomes;
    }
}
