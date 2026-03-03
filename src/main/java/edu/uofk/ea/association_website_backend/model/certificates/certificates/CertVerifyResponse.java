package edu.uofk.ea.association_website_backend.model.certificates.certificates;

import edu.uofk.ea.association_website_backend.model.certificates.DocStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class CertVerifyResponse {
    private boolean valid;
    private Integer id;
    private String name;
    private String event;
    private LocalDate endDate;
    private List<String> outComes;
    private double percentageDegree;
    private DocStatus status;
    private Instant issueDate;

    public CertVerifyResponse() {
        this.valid = false;
    }

    public CertVerifyResponse(Integer id, String name, String event, LocalDate endDate, List<String> outComes, double percentageDegree, DocStatus status, Instant issueDate) {
        this.valid = true;
        this.id = id;
        this.name = name;
        this.event = event;
        this.endDate = endDate;
        this.outComes = outComes;
        this.percentageDegree = percentageDegree;
        this.status = status;
        this.issueDate = issueDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getOutComes() {
        return outComes;
    }

    public void setOutComes(List<String> outComes) {
        this.outComes = outComes;
    }

    public double getPercentageDegree() {
        return percentageDegree;
    }

    public void setPercentageDegree(double percentageDegree) {
        this.percentageDegree = percentageDegree;
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
