package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.feedback.FeedbackRequest;
import edu.uofk.ea.association_website_backend.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService service;

    @Autowired
    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @PostMapping
    public void submitFeedback(@Valid @RequestBody FeedbackRequest request) {
        service.submitFeedback(request);
    }
}