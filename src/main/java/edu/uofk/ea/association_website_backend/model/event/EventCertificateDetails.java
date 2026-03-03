package edu.uofk.ea.association_website_backend.model.event;

import edu.uofk.ea.association_website_backend.model.Language;

import java.time.LocalDate;
import java.util.List;

public class EventCertificateDetails {
    private String eventName;
    private EventType eventType;
    private List<String> outcomes;
    private LocalDate startDate;
    private LocalDate endDate;

    private String studentNameAr;
    private String studentNameEn;
    private String studentEmail;
    private Double percentageGrade;

    private Language lang;

    public EventCertificateDetails(String eventName, EventType eventType, List<String> outcomes, LocalDate startDate, LocalDate endDate, String studentNameAr, String studentNameEn, String studentEmail, Double percentageGrade) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.outcomes = outcomes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studentNameAr = studentNameAr;
        this.studentNameEn = studentNameEn;
        this.percentageGrade = percentageGrade;
        this.studentEmail = studentEmail;
        this.lang = Language.en;
    }

    public String getEventName() {
        return eventName;
    }

    public List<String> getOutcomes() {
        return outcomes;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStudentName() {
        if (lang == Language.ar) return studentNameAr;
        return studentNameEn;
    }

    public Double getPercentageGrade() {
        return percentageGrade;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public Language getLang() {
        return lang;
    }
}
