package edu.uofk.ea.association_website_backend.service.certificates;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.model.certificates.DocStatus;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.CertVerifyResponse;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.CertificateModel;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.MassGenerateCertsRequest;
import edu.uofk.ea.association_website_backend.model.certificates.certificates.GenerateCertRequest;
import edu.uofk.ea.association_website_backend.model.certificates.documents.DocVerifyResponse;
import edu.uofk.ea.association_website_backend.model.certificates.documents.DocumentCertRequest;
import edu.uofk.ea.association_website_backend.model.certificates.documents.DocumentModel;
import edu.uofk.ea.association_website_backend.model.event.EventCertificateDetails;
import edu.uofk.ea.association_website_backend.model.event.EventMassCertificateDetails;
import edu.uofk.ea.association_website_backend.model.event.EventModel;
import edu.uofk.ea.association_website_backend.repository.StudentRepo;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.CloudinaryService;
import edu.uofk.ea.association_website_backend.service.MailService;
import edu.uofk.ea.association_website_backend.service.TobClient;
import edu.uofk.ea.association_website_backend.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

import static edu.uofk.ea.association_website_backend.service.certificates.HashingService.SHA256;

//@Async
@Service
public class CertificateManagerService {

    @Value("${app.secret.salt}")
    private String secretSalt;
    @Value("${app.website.link}")
    private String websiteLink;

    private final EventService eventService;

    private final PdfService pdfService;
    private final ThymeleafService thymeleafService;
    private final ZXingService zXingService;
    private final CertificatesService certificateService;
    private final DocumentsService documentsService;
    private final CloudinaryService cloudinaryService;
    private final MailService mailService;
    private final AdminDetailsService adminService;
    private final TobClient tobClient;

    @Autowired
    public CertificateManagerService(
            EventService eventService,
            PdfService pdfService,
            ThymeleafService thymeleafService,
            ZXingService zXingService,
            CertificatesService certificateService,
            DocumentsService documentsService,
            CloudinaryService cloudinaryService,
            MailService mailService,
            AdminDetailsService adminService,
            TobClient tobClient
    ) {
        this.eventService = eventService;
        this.pdfService = pdfService;
        this.thymeleafService = thymeleafService;
        this.zXingService = zXingService;
        this.certificateService = certificateService;
        this.documentsService = documentsService;
        this.cloudinaryService = cloudinaryService;
        this.mailService = mailService;
        this.adminService = adminService;
        this.tobClient = tobClient;
    }

    @Transactional
    public void HandleDefaultOneCert(GenerateCertRequest request, String token) {
        EventCertificateDetails details = eventService.getCertDetails(request.getEventId(), request.getStudentId());
        details.setLang(request.getLang());

        String stringToHash = details.getStudentName() + "|" + details.getEventName() + "|" + details.getStartDate() + "|" + details.getEndDate() + "|" + request.getLang() + "|" + secretSalt;

        /// Step 1: Create the hash
        String hashedString = SHA256(stringToHash);

        /// Step 2: Create the QR code
        String url = websiteLink + "/cert/verify/" + hashedString;
        byte[] qrCode = zXingService.generateQRCodeBase64(url, 512, 512);

        /// Step 3: Generate the certificate
        String certHTML = thymeleafService.generateDefaultCertHtml(details);
        byte[] pdf = tobClient.generatePdf(certHTML, token);

        /// Step 4: Sign the certificate PDF
        byte[] signedPdf = pdfService.signPdf(pdf, new ByteArrayInputStream(qrCode), PdfService.Orientation.Landscape);

        // Save certificate to a file for testing
        try (FileOutputStream fos = new FileOutputStream(details.getEventName() + "-" + details.getStudentName() + ".pdf")) {
            fos.write(signedPdf);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save test certificate: " + e.getMessage());
        }


        /// Step 5: Upload to storage
        String link = cloudinaryService.uploadDoc(signedPdf, hashedString);

        /// Step 6: Save the record with the returned 'link' to the database.
        CertificateModel model = new CertificateModel(
                hashedString,
                request.getStudentId(),
                request.getEventId(),
                request.getLang(),
                link,
                DocStatus.ACTIVE
        );
        certificateService.save(model);

        /// Step 7: Send an email with the link to the student.
        String emailHtml = thymeleafService.generateEmailCertHtml(details.getStudentName(), details.getEventType(), details.getEventName(), url);
        mailService.CertificateEmail(
                details.getStudentEmail(),
                "Certificate for " + details.getEventName(),
                emailHtml
        );
    }

    public void HandleDefaultManyCerts(MassGenerateCertsRequest request, String token) {
        EventMassCertificateDetails details = eventService.getAllDetails(request.getStudentIds(), request.getEventId());
        details.setLang(request.getLang());

        for (int studentId : request.getStudentIds()) {

            String stringToHash = details.getStudentName().get(studentId) + "|" + details.getEventName() + "|" + details.getStartDate() + "|" + details.getEndDate() + "|" + request.getLang() + "|" + secretSalt;

            /// Step 1: Create the hash
            String hashedString = SHA256(stringToHash);

            /// Step 2: Create the QR code
            String url = websiteLink + "/cert/verify/" + hashedString;
            byte[] qrCode = zXingService.generateQRCodeBase64(url, 512, 512);

            /// Step 3: Generate the certificate
            String certHTML = thymeleafService.generateDefaultCertHtml(details.getOne(studentId));
            byte[] pdf = tobClient.generatePdf(certHTML, token);

            /// Step 4: Sign the certificate PDF
            byte[] signedPdf = pdfService.signPdf(pdf, new ByteArrayInputStream(qrCode), PdfService.Orientation.Landscape);

            /// Step 5: Upload to storage
            String link = cloudinaryService.uploadDoc(signedPdf, hashedString);

            /// Step 6: Save the record with the returned 'link' to the database.
            CertificateModel model = new CertificateModel(
                    hashedString,
                    studentId,
                    request.getEventId(),
                    details.getLang(),
                    link,
                    DocStatus.ACTIVE
            );
            certificateService.save(model);

            /// Step 7: Send an email with the link to the student.
            String emailHtml = thymeleafService.generateEmailCertHtml(details.getStudentName().get(studentId), details.getEventType(), details.getEventName(), url);
            mailService.CertificateEmail(
                    details.getStudentEmails().get(studentId),
                    "Certificate for " + details.getEventName(),
                    emailHtml
            );
        }
    }

    public byte[] HandleDocumentCert(DocumentCertRequest r, byte[] originalPdf, String adminName) {
        String stringToHash = r.getCertifyingAuthority() + "|" + r.getDocumentAuthor() + "|" + r.getDocumentReason() + "|" + r.getDocumentType() + "|" + r.getLang() + "|" + secretSalt;

        /// Step 1: Create the hash
        String hashedString = SHA256(stringToHash);

        /// Step 2: Create the QR code
        String url = websiteLink + "/cert/verify/doc/" + hashedString;
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
        CertificateModel cert = certificateService.findByHash(hash);
        if (cert == null) return new CertVerifyResponse();
        EventCertificateDetails details = eventService.getCertDetails(cert.getEventId(), cert.getStudentId());

        return new CertVerifyResponse(
                cert.getId(),
                details.getStudentName(),
                details.getEventName(),
                details.getEndDate(),
                details.getOutcomes(),
                details.getPercentageGrade(),
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

    public byte[] DownloadCertificate(int id, Language lang) {
        CertificateModel certificate = certificateService.findById(id);
        if (certificate == null) throw new GenericNotFoundException("Certificate not found");

        if (certificate.getLang() == lang) return StreamFile(certificate.getFilePath());

        CertificateModel certificateInRequestedLang = certificateService.findByStudentIdAndEventIdAndLanguage(certificate.getStudentId(), certificate.getEventId(), lang);
        if (certificateInRequestedLang == null) throw new GenericNotFoundException("Certificate not found in the requested language");

        return StreamFile(certificateInRequestedLang.getFilePath());
    }

    private byte[] StreamFile(String filePath){
        try (InputStream in = new URL(filePath).openStream()) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file from Cloudinary", e);
        }
    }
}
