package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.HtmlGenerationException;
import edu.uofk.ea.association_website_backend.model.HtmlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TobClient {

    private final RestClient restClient;

    public TobClient(@Value("${app.tob.url}") String tobUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(tobUrl)
                .build();
    }

    public byte[] generatePdf(String html, String jwt) {
        HtmlRequest request = new HtmlRequest(html);

        return restClient.post()
                .uri("/api/v1/helper/pdf")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new HtmlGenerationException("Failed to generate PDF: Invalid HTML content or request.");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new HtmlGenerationException("Failed to generate PDF: External service error.");
                })
                .body(byte[].class);
    }
}
