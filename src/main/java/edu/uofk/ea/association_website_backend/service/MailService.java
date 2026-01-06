package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.model.VisitorFormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${mail.receiver}")
    private String defaultReceiver;

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender sender;

    @Autowired
    public MailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void visitorFormMessageSend(VisitorFormModel form) {
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
}
