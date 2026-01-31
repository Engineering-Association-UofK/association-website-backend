package edu.uofk.ea.association_website_backend.model.certificates.certificates;

import edu.uofk.ea.association_website_backend.model.certificates.DocStatus;

import java.time.Instant;

public class CertVerifyResponse {
    private boolean valid;
    private Integer id;
    private String link;
    private String name;
    private String event;
    private DocStatus status;
    private Instant issueDate;

    public CertVerifyResponse(boolean valid, Integer id, String link, String name, String event, DocStatus status, Instant issueDate) {
        this.valid = valid;
        this.id = id;
        this.link = link;
        this.name = name;
        this.event = event;
        this.status = status;
        this.issueDate = issueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public DocStatus getStatus() {
        return status;
    }

    public void setStatus(DocStatus status) {
        this.status = status;
    }

    public Instant getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }
}
