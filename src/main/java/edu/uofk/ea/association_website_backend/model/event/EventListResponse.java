package edu.uofk.ea.association_website_backend.model.event;

public class EventListResponse {
    private int id;
    private String name;
    private EventType eventType;
    private int maxParticipants;
    private String startDate;
    private String endDate;

    public EventListResponse(EventModel event) {
        this.id = event.getId();
        this.name = event.getName();
        this.eventType = event.getEventType();
        this.maxParticipants = event.getMaxParticipants();
        this.startDate = event.getStartDate().toString();
        this.endDate = event.getEndDate().toString();
    }

    public EventListResponse(int id, String name, EventType eventType, int maxParticipants, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.eventType = eventType;
        this.maxParticipants = maxParticipants;
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
}
