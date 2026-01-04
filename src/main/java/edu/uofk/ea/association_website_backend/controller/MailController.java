package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.VisitorForm;
import edu.uofk.ea.association_website_backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/api/mail/visitor-form")
    public void visitorFormMessageSend(@RequestBody VisitorForm request) {
        mailService.visitorFormMessageSend(request);
    }

}
