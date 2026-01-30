package edu.uofk.ea.association_website_backend.model.certificates.documents;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="documents")
public class DocumentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "document_hash")
    private String documentHash;

    @Column(name = "certifying_authority")
    private String certifyingAuthority;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private DocumentTypes documentType;

    @Column(name = "document_reason")
    private String documentReason;

    @Column(name = "document_author")
    private String decisionAuthor;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "issue_date")
    private Instant issueDate;

    @Column(name = "file_path")
    private String filePath;

    public DocumentModel() {
    }


    public DocumentModel(String documentHash, String certifyingAuthority, DocumentTypes documentType, String documentReason, String documentAuthor, Integer adminId, String filePath) {
        this.documentHash = documentHash;
        this.certifyingAuthority = certifyingAuthority;
        this.documentType = documentType;
        this.documentReason = documentReason;
        this.decisionAuthor = documentAuthor;
        this.adminId = adminId;
        this.issueDate = Instant.now();
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentHash() {
        return documentHash;
    }

    public void setDocumentHash(String documentHash) {
        this.documentHash = documentHash;
    }

    public String getCertifyingAuthority() {
        return certifyingAuthority;
    }

    public void setCertifyingAuthority(String certifyingAuthority) {
        this.certifyingAuthority = certifyingAuthority;
    }

    public DocumentTypes getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypes documentType) {
        this.documentType = documentType;
    }

    public String getDocumentReason() {
        return documentReason;
    }

    public void setDocumentReason(String documentReason) {
        this.documentReason = documentReason;
    }

    public String getDecisionAuthor() {
        return decisionAuthor;
    }

    public void setDecisionAuthor(String decisionAuthor) {
        this.decisionAuthor = decisionAuthor;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
}
