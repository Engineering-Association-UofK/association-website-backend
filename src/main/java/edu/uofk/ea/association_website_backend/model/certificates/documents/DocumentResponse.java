package edu.uofk.ea.association_website_backend.model.certificates.documents;

public class DocumentResponse {
    private byte[] pdf;

    public DocumentResponse(byte[] pdf) {
        this.pdf = pdf;
    }

    public byte[] getLink() {
        return pdf;
    }

    public void setLink(byte[] pdf) {
        this.pdf = pdf;
    }

}
