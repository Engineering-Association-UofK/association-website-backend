package edu.uofk.ea.association_website_backend.model.certificates.certificates;


public class DefaultOneCertRequest {
    private Integer studentId;
    private Integer eventId;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
