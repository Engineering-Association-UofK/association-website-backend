package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.certificates.certificates.CertVerifyResponse;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.DefaultManyCertsRequest;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.DefaultOneCertRequest;
import edu.uofk.ea.association_website_backend.model.certificates.documents.DocVerifyResponse;
import edu.uofk.ea.association_website_backend.model.certificates.documents.DocumentCertRequest;
import edu.uofk.ea.association_website_backend.service.certificates.CertificateManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@RestController
@RequestMapping("api/documents")
public class CertificatesController {

    private final CertificateManagerService manager;
    private final ObjectMapper objectMapper;

    @Autowired
    public CertificatesController(CertificateManagerService manager, ObjectMapper objectMapper) {
        this.manager = manager;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/cert")
    @PreAuthorize("hasAnyRole('CERTIFICATE_ISSUER', 'SUPER_ADMIN')")
    public void newDefaultCert(@RequestBody DefaultOneCertRequest request) {
        manager.HandleDefaultOneCert(request.getStudentId(), request.getEventId());
    }

    @PostMapping("/cert/mass")
    @PreAuthorize("hasAnyRole('CERTIFICATE_ISSUER', 'SUPER_ADMIN')")
    public void newDefaultCerts(@RequestBody DefaultManyCertsRequest request) {
        manager.HandleDefaultManyCerts(request);
    }

    ///
    /// This endpoint is used to sign documents and provide them with a QR Code that leads
    /// to the verification page. It accepts a PDF file and the document details,
    /// then it returns the signed PDF file.
    ///
    @PostMapping(path = "/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('PAPER_CERTIFIER', 'SUPER_ADMIN')")
    public ResponseEntity<byte[]> newDocumentCert(
            @RequestPart("data") String requestString,
            @RequestPart("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {
        DocumentCertRequest request = objectMapper.readValue(requestString, DocumentCertRequest.class);
        String name = authentication.getName();
        byte[] pdfBytes = manager.HandleDocumentCert(request, file.getBytes(), name);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document_certified.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping("/certificate/verify/{hash}")
    public CertVerifyResponse verifyCertificate(@PathVariable String hash){
        return manager.VerifyCertificate(hash);
    }
    @GetMapping("/document/verify/{hash}")
    public DocVerifyResponse verifyDocument(@PathVariable String hash){
        return manager.VerifyDocument(hash);
    }

    @GetMapping("/document/download/{id}")
    @PreAuthorize("hasAnyRole('PAPER_VIEWER', 'SUPER_ADMIN')")
    public ResponseEntity<byte[]> DownloadDocument(@PathVariable int id) {
        byte[] pdf = manager.DownloadDocument(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/certificate/download/{id}")
    @PreAuthorize("hasAnyRole('PAPER_VIEWER', 'SUPER_ADMIN')")
    public ResponseEntity<byte[]> DownloadCertificate(@PathVariable int id) {
        byte[] pdf = manager.DownloadCertificate(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);

    }

}
