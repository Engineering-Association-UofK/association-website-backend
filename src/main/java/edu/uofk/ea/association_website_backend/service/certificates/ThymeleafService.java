package edu.uofk.ea.association_website_backend.service.certificates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;

@Service
public class ThymeleafService {

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public ThymeleafService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateDefaultHtml(String name, String event) {
        Context context = new Context();
        context.setVariable("studentName", name);
        context.setVariable("eventName", event);
        context.setVariable("issueDate", LocalDate.now().toString());

        return templateEngine.process("certificate", context);
    }

    public String generateDecisionHtml(
            String certifyingAuthority,
            String decisionNumber,
            String decisionName,
            String decisionContent,
            String decisionMaker,
            String sealPath,
            String qrBase64
    ) {
        Context context = new Context();
        context.setVariable("CertifyingAuthority", certifyingAuthority);
        context.setVariable("DecisionNumber", decisionNumber);
        context.setVariable("DecisionName", decisionName);
        context.setVariable("DecisionContent", decisionContent);
        context.setVariable("DecisionMaker", decisionMaker);
        context.setVariable("seal", sealPath);
        context.setVariable("qrCode", qrBase64);

        return templateEngine.process("decision_template", context);
    }
}
