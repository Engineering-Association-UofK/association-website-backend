package edu.uofk.ea.association_website_backend.model.certificates.certificates;


import edu.uofk.ea.association_website_backend.model.Language;

import java.util.List;

public class MassGenerateCertsRequest {
    private List<Integer> studentIds;
    private Integer eventId;
    public Language lang;

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

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }
}
