package edu.uofk.ea.association_website_backend.service.certificates;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class HtmlToPdfService {

    public void convertHtmlToPdf(String html, String outputFilePath) {
        try (OutputStream os = new FileOutputStream(outputFilePath)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();

            System.out.println("PDF Created at: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
