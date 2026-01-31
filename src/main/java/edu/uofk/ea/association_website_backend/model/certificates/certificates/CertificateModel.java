package edu.uofk.ea.association_website_backend.model.certificates.certificates;


import edu.uofk.ea.association_website_backend.model.certificates.DocStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="certificates")
public class CertificateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cert_hash")
    private String certHash;

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "issue_date")
    private Instant issueDate;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "status")
    private DocStatus status;


    public CertificateModel() {}

    public CertificateModel(String certHash, Integer studentId, Integer eventId, String filePath, DocStatus status) {
        this.certHash = certHash;
        this.studentId = studentId;
        this.eventId = eventId;
        this.filePath = filePath;
        this.issueDate = Instant.now();
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCertHash() {
        return certHash;
    }

    public void setCertHash(String certHash) {
        this.certHash = certHash;
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

    public Instant getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public DocStatus getStatus() {
        return status;
    }

    public void setStatus(DocStatus status) {
        this.status = status;
    }
}
