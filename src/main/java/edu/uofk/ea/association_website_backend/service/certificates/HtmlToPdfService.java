package edu.uofk.ea.association_website_backend.service.certificates;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class HtmlToPdfService {

    public static void convertHtmlToPdf(String html, String outputFilePath) {
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
