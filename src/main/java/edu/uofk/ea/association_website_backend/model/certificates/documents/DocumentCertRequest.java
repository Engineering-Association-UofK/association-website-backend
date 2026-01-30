package edu.uofk.ea.association_website_backend.model.certificates.documents;

public class DocumentCertRequest {
    private String certifyingAuthority;
    private String documentReason;
    private String documentAuthor;
    private DocumentTypes documentType;

    public DocumentTypes getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypes documentType) {
        this.documentType = documentType;
    }

    public String getCertifyingAuthority() {
        return certifyingAuthority;
    }

    public void setCertifyingAuthority(String certifyingAuthority) {
        this.certifyingAuthority = certifyingAuthority;
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
}
