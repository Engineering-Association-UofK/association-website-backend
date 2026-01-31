package edu.uofk.ea.association_website_backend.model.certificates.documents;

import edu.uofk.ea.association_website_backend.model.certificates.DocStatus;

import java.time.Instant;

public class DocVerifyResponse {
    private boolean valid;
    private Integer id;
    private String certifyingAuthority;
    private DocumentTypes documentType;
    private String documentReason;
    private String documentAuthor;
    private Instant issueDate;
    private DocStatus status;

    public DocVerifyResponse(boolean valid, Integer id, String certifyingAuthority, DocumentTypes documentType, String documentReason, String documentAuthor, Instant issueDate, DocStatus status) {
        this.valid = valid;
        this.id = id;
        this.certifyingAuthority = certifyingAuthority;
        this.documentType = documentType;
        this.documentReason = documentReason;
        this.documentAuthor = documentAuthor;
        this.issueDate = issueDate;
        this.status = status;
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

    public String getDocumentAuthor() {
        return documentAuthor;
    }

    public void setDocumentAuthor(String documentAuthor) {
        this.documentAuthor = documentAuthor;
    }

    public Instant getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }

    public DocStatus getStatus() {
        return status;
    }

    public void setStatus(DocStatus status) {
        this.status = status;
    }
}
