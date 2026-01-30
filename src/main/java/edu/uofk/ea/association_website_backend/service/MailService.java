package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.model.VisitorMessageModel;
import edu.uofk.ea.association_website_backend.repository.VisitorMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Async
public class MailService {

    @Value("${mail.receiver}")
    private String defaultReceiver;

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender sender;
    private final VisitorMessageRepo repo;

    @Autowired
    public MailService(JavaMailSender sender, VisitorMessageRepo repo) {
        this.sender = sender;
        this.repo = repo;
    }

    public void visitorFormMessageSend(VisitorMessageModel form) {
        form.setCreatedAt(Instant.now());
        repo.save(form);

        String subject = "New Visitor Form";
        String text = "Name: " + form.getName() + "\nEmail: " + form.getEmail() + "\nMessage: " + form.getMessage();
        sendEmail(defaultReceiver, subject, text);
    }

    public void sendVerificationCode(String email, String code) {
        String subject = "Verification code for SEA account";
        String text = "Your account was used to create a new SEA account. If this was not you, please ignore this email." +
                "\n\nYour verification code is: " + code
                + "\nThis code will expire in 10 minutes. \n\nThank you!";
        sendEmail(email, subject, text);
    }

    public void sendEmail(String receiver, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(text);
        sender.send(message);
    }

    public void CertificateEmail(String email, String link, String name, String event){
        String subject = "Your Certificate for " + event;
        String text ="Dear " + name + ",\n\n" +
                "Congratulations on successfully completing the event + " + event + ".\n\n" +
                "You can view and download your certificate using the following link: \n" +
                link + "\n\n" + "Engineering Association";
        sendEmail(email, subject, text);
    }
}
