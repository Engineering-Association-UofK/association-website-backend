package edu.uofk.ea.association_website_backend.model.event;

import java.util.Map;

public class ParticipantsResponse {
    private int id;
    private int eventId;
    private int studentId;
    private String studentNameAr;
    private String studentNameEn;
    private String studentEmail;
    private Map<String, Double> components;
    private Double average;

    public ParticipantsResponse(int id, int eventId, int studentId, String studentNameAr, String studentNameEn, String studentEmail, Map<String, Double> components, Double average) {
        this.id = id;
        this.eventId = eventId;
        this.studentId = studentId;
        this.studentNameAr = studentNameAr;
        this.studentNameEn = studentNameEn;
        this.studentEmail = studentEmail;
        this.components = components;
        this.average = average;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentNameAr() {
        return studentNameAr;
    }

    public void setStudentNameAr(String studentNameAr) {
        this.studentNameAr = studentNameAr;
    }

    public String getStudentNameEn() {
        return studentNameEn;
    }

    public void setStudentNameEn(String studentNameEn) {
        this.studentNameEn = studentNameEn;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Map<String, Double> getComponents() {
        return components;
    }

    public void setComponents(Map<String, Double> components) {
        this.components = components;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
