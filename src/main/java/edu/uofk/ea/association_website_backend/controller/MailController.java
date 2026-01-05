package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.VisitorFormModel;
import edu.uofk.ea.association_website_backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/visitor-form")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void visitorFormMessageSend(@RequestBody VisitorFormModel request) {
        mailService.visitorFormMessageSend(request);
    }

}
