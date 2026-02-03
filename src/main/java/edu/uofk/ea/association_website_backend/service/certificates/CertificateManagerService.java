package edu.uofk.ea.association_website_backend.service.certificates;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.certificates.DocStatus;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.CertVerifyResponse;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.CertificateModel;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.DefaultManyCertsRequest;
import edu.uofk.ea.association_website_backend.model.certificates.documents.DocVerifyResponse;
import edu.uofk.ea.association_website_backend.model.certificates.documents.DocumentCertRequest;
import edu.uofk.ea.association_website_backend.model.certificates.documents.DocumentModel;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.CloudinaryService;
import edu.uofk.ea.association_website_backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;

import static edu.uofk.ea.association_website_backend.service.certificates.HashingService.SHA256;

@Service
public class CertificateManagerService {

    @Value("${app.secret.salt}")
    private String secretSalt;
    @Value("${app.website.link}")
    private String websiteLink;

    private final PdfService pdfService;
    private final ThymeleafService thymeleafService;
    private final ZXingService zXingService;
    private final CertificatesService certificateService;
    private final DocumentsService documentsService;
    private final CloudinaryService cloudinaryService;
    private final MailService mailService;
    private final AdminDetailsService adminService;

    @Autowired
    public CertificateManagerService(
            PdfService pdfService,
            ThymeleafService thymeleafService,
            ZXingService zXingService,
            CertificatesService certificateService,
            DocumentsService documentsService,
            CloudinaryService cloudinaryService,
            MailService mailService,
            AdminDetailsService adminService
    ) {
        this.pdfService = pdfService;
        this.thymeleafService = thymeleafService;
        this.zXingService = zXingService;
        this.certificateService = certificateService;
        this.documentsService = documentsService;
        this.cloudinaryService = cloudinaryService;
        this.mailService = mailService;
        this.adminService = adminService;
    }

    public void HandleDefaultOneCert(int StudentId, int EventId) {
        // TODO: Replace with actual student and Event names when they are ready
        String StudentName = "john doe"; // StudentRepo.get(request.getStudentId());
        String EventName = "HTML Basics"; // EventRepo.get(request.getEventId());
        String stringToHash = StudentName + "|" + EventName + "|" + secretSalt;

        /// Step 1: Create the hash
        String hashedString = SHA256(stringToHash);

        /// Step 2: Create the QR code
        String url = websiteLink + "/cert/verify/" + hashedString;
        byte[] qrCode = zXingService.generateQRCodeBase64(url, 512, 512);

        /// Step 3: Generate the certificate
        String certHTML = thymeleafService.generateDefaultHtml(StudentName, EventName);
        byte[] pdf = pdfService.convertHtmlToPdf(certHTML);

        /// Step 4: Sign the certificate PDF
        byte[] signedPdf = pdfService.signPdf(pdf, new ByteArrayInputStream(qrCode), PdfService.Orientation.Landscape);

        /// Step 5: Upload to storage
        String link = cloudinaryService.uploadDoc(signedPdf, hashedString);

        /// Step 6: Save the record with the returned 'link' to the database.
        CertificateModel model = new CertificateModel(
                hashedString,
                StudentId,
                EventId,
                link,
                DocStatus.ACTIVE
        );
        certificateService.Save(model);

        /// Step 7: Send an email with the link to the student.
        mailService.CertificateEmail(
                "unvacc80@gmail.com", // StudentRepo.get(request.getStudentId()).getEmail(),
                url,
                StudentName,
                EventName
        );
    }

    public void HandleDefaultManyCerts(DefaultManyCertsRequest request) {
        for (int studentId : request.getStudentIds()){
            HandleDefaultOneCert(studentId, request.getEventId());
        }
    }

    public byte[] HandleDocumentCert(DocumentCertRequest r, byte[] originalPdf, String adminName) {
        String stringToHash = r.getCertifyingAuthority() + "|" + r.getDocumentAuthor() + "|" + r.getDocumentReason() + "|" + r.getDocumentType() + "|" + secretSalt;

        /// Step 1: Create the hash
        String hashedString = SHA256(stringToHash);

        /// Step 2: Create the QR code
        String url = websiteLink + "/cert/verify/" + hashedString;
        byte[] qrCode = zXingService.generateQRCodeBase64(url, 512, 512);

        /// Step 3: Sign the certificate PDF with the QR Code
        byte[] signedPdf = pdfService.signPdf(originalPdf, new ByteArrayInputStream(qrCode), PdfService.Orientation.Portrait);

        /// Step 4: Upload to storage
        String link = cloudinaryService.uploadDoc(signedPdf, hashedString);

        /// Step 5: Save the record with the returned 'link' to the database.
        DocumentModel model = new DocumentModel(
                hashedString,
                r.getCertifyingAuthority(),
                r.getDocumentType(),
                r.getDocumentReason(),
                r.getDocumentAuthor(),
                adminService.getId(adminName),
                link,
                DocStatus.ACTIVE
                );
        documentsService.Save(model);

        return signedPdf;
    }

    public CertVerifyResponse VerifyCertificate(String hash) {
        CertificateModel cert = certificateService.GetByHash(hash);

        if (cert == null) return new CertVerifyResponse(false, null,null, null, null, null, null);

        return new CertVerifyResponse(
                true,
                cert.getId(),
                cert.getFilePath(), // TODO: Replace with actual student and Event names when they are ready
                "John Doe", // StudentRepo.get(cert.getStudentId()).getName(),
                "HTML Basics", // EventRepo.get(cert.getEventId()).getName()",
                cert.getStatus(),
                cert.getIssueDate()
        );
    }

    public DocVerifyResponse VerifyDocument(String hash) {
        DocumentModel decision = documentsService.GetByHash(hash);

        if (decision == null) return new DocVerifyResponse(false, null, null, null, null, null, null, null);
        
        return new DocVerifyResponse(
                true,
                decision.getId(),
                decision.getCertifyingAuthority(),
                decision.getDocumentType(),
                decision.getDocumentReason(),
                decision.getDecisionAuthor(),
                decision.getIssueDate(),
                decision.getStatus()
        );
    }

    public byte[] DownloadDocument(int id) {
        DocumentModel document = documentsService.GetById(id);

        if (document == null) throw new GenericNotFoundException("Document not found");

        return StreamFile(document.getFilePath());
    }

    public byte[] DownloadCertificate(int id) {
        CertificateModel document = certificateService.GetById(id);

        if (document == null) throw new GenericNotFoundException("Document not found");
        if (document.getFilePath() == null) throw new GenericNotFoundException("Document not found");

        return StreamFile(document.getFilePath());
    }

    private byte[] StreamFile(String filePath){
        try (InputStream in = new java.net.URI(filePath).toURL().openStream()) {
            return in.readAllBytes();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to download file from Cloudinary", e);
        }
    }
}
