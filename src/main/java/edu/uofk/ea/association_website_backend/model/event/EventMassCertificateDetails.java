package edu.uofk.ea.association_website_backend.model.event;

import edu.uofk.ea.association_website_backend.model.Language;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EventMassCertificateDetails {
    private final String eventName;
    private final EventType eventType;
    private final List<String> outcomes;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private final Map<Integer, String> studentNameAr;
    private final Map<Integer, String> studentNameEn;
    private final Map<Integer, Double> percentageGrade;

    private Language lang;

    public EventMassCertificateDetails(String eventName, EventType eventType, List<String> outcomes, LocalDate startDate, LocalDate endDate, Map<Integer, String> studentNameAr, Map<Integer, String> studentNameEn, Map<Integer, Double> percentageGrade) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.outcomes = outcomes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studentNameAr = studentNameAr;
        this.studentNameEn = studentNameEn;
        this.percentageGrade = percentageGrade;
        this.lang = Language.en;
    }

    public EventCertificateDetails getOne(int studentId) {
        var details = new EventCertificateDetails(
                eventName,
                eventType,
                outcomes,
                startDate,
                endDate,
                studentNameAr.get(studentId),
                studentNameEn.get(studentId),
                percentageGrade.get(studentId)
        );
        details.setLang(lang);
        return details;
    }

    public Map<Integer, String> getStudentName() {
        if (lang == Language.ar) return studentNameAr;
        return studentNameEn;
    }

    public String getEventName() {
        return eventName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public List<String> getOutcomes() {
        return outcomes;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Map<Integer, Double> getPercentageGrade() {
        return percentageGrade;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public Language getLang() {
        return lang;
    }
}
