package edu.uofk.ea.association_website_backend.model.certificates.certificates;


import java.util.List;

public class DefaultManyCertsRequest {
    private List<Integer> studentIds;
    private Integer eventId;

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentId) {
        this.studentIds = studentId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
