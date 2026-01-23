package edu.uofk.ea.association_website_backend.service.certificates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ThymeleafService {

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public ThymeleafService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateHtml(String name, String event, String qrBase64) {
        Context context = new Context();
        context.setVariable("studentName", name);
        context.setVariable("eventName", event);
        context.setVariable("issueDate", LocalDate.now().toString());
        context.setVariable("qrCode", qrBase64);

        return templateEngine.process("certificate", context);
    }
}
