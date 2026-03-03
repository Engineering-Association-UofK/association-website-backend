package edu.uofk.ea.association_website_backend.service.certificates;

import edu.uofk.ea.association_website_backend.model.event.EventCertificateDetails;
import edu.uofk.ea.association_website_backend.model.event.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ThymeleafService {

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public ThymeleafService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateEmailCertHtml(String studentName, EventType eventType, String eventName, String url) {
        Context context = new Context();
        context.setVariable("studentName", studentName);
        context.setVariable("eventType", eventType);
        context.setVariable("eventName", eventName);
        context.setVariable("certUrl", url);

        return templateEngine.process("email-cert", context);
    }

    public String generateDefaultCertHtml(EventCertificateDetails details) {
        var columns = chunkTasks(details.getOutcomes());
        String startDate = formatDate(details.getStartDate(), details.getLang().name().toLowerCase());
        String endDate = formatDate(details.getEndDate(), details.getLang().name().toLowerCase());

        Context context = new Context();
        context.setVariable("studentName", details.getStudentName());
        context.setVariable("eventName", details.getEventName());
        context.setVariable("startDate", details.getStartDate());
        context.setVariable("endDate", details.getEndDate());
        context.setVariable("grade", details.getPercentageGrade());
        context.setVariable("taskColumns", columns);

        return templateEngine.process("cert-"+details.getLang().name().toLowerCase(), context);
    }

    private String formatDate(LocalDate date, String locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.of(locale));
        return date.format(formatter);
    }

    private List<List<String>> chunkTasks(List<String> tasks) {
        List<List<String>> columns = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i += 3) {
            columns.add(tasks.subList(i, Math.min(i + 3, tasks.size())));
        }

        return columns;
    }
}
