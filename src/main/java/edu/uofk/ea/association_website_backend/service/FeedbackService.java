package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.model.ContactListModel;
import edu.uofk.ea.association_website_backend.model.feedback.FeedbackModel;
import edu.uofk.ea.association_website_backend.model.feedback.FeedbackRequest;
import edu.uofk.ea.association_website_backend.repository.ContactListRepo;
import edu.uofk.ea.association_website_backend.repository.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepo repo;
    private final ContactListRepo contactListRepo;
    private final MailService emailService;

    @Autowired
    public FeedbackService(FeedbackRepo repo, ContactListRepo contactListRepo, MailService emailService) {
        this.repo = repo;
        this.contactListRepo = contactListRepo;
        this.emailService = emailService;
    }

    public void submitFeedback(FeedbackRequest request) {
        if (request.getMessage() != null && request.getSenderContact().isBlank()) request.setSenderContact(null);
        // 1. Save to Database
        FeedbackModel model = new FeedbackModel();
        model.setMessage(request.getMessage());
        model.setSenderContact(request.getSenderContact());
        repo.save(model);

        // 2. Send Email
        String subject = "New Technical Feedback Received";
        String body = "You have received new feedback:\n\n" +
                "Message: " + request.getMessage() + "\n" +
                "Sender: " + (request.getSenderContact() != null ? request.getSenderContact() : "Anonymous");

        ContactListModel contact = contactListRepo.findByName("support");
        if (contact != null) {
            emailService.sendEmail(contact.getEmail(), subject, body);
        }
    }
}