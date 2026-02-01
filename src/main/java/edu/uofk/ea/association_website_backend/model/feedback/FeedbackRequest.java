package edu.uofk.ea.association_website_backend.model.feedback;

import jakarta.validation.constraints.NotBlank;

public class FeedbackRequest {
    @NotBlank(message = "Message is required")
    private String message;

    private String senderContact;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderContact() {
        return senderContact;
    }

    public void setSenderContact(String senderContact) {
        this.senderContact = senderContact;
    }
}