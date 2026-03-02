package edu.uofk.ea.association_website_backend.model.event;

import java.util.List;

public class EventResponse {
    private int id;
    private String name;
    private EventType eventType;
    private int maxParticipants;
    private String startDate;
    private String endDate;
    private int participants;
    private List<Integer> participantsIds;

    public EventResponse(int id, String name, EventType eventType, int maxParticipants, String startDate, String endDate, int participants, List<Integer> participantsIds) {
        this.id = id;
        this.name = name;
        this.eventType = eventType;
        this.maxParticipants = maxParticipants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participants = participants;
        this.participantsIds = participantsIds;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public List<Integer> getParticipantsIds() {
        return participantsIds;
    }

    public void setParticipantsIds(List<Integer> participantsIds) {
        this.participantsIds = participantsIds;
    }
}
