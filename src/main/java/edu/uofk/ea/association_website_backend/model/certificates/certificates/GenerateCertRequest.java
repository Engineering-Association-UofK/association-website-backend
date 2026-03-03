package edu.uofk.ea.association_website_backend.model.certificates.certificates;


import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.validation.constraints.NotNull;

public class GenerateCertRequest {
    @NotNull(message = "Student ID is required")
    private Integer studentId;
    @NotNull(message = "Event ID is required")
    private Integer eventId;
    @NotNull(message = "Language is required")
    private Language lang;

    public GenerateCertRequest(Integer studentId, Integer eventId, Language lang) {
        this.studentId = studentId;
        this.eventId = eventId;
        this.lang = lang;
    }

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
    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }
}
