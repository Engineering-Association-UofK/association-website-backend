package edu.uofk.ea.association_website_backend.service.certificates;

import edu.uofk.ea.association_website_backend.model.certificates.ApplyCertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static edu.uofk.ea.association_website_backend.service.certificates.HashingService.SHA256;

@Service
public class CertificateManagerService {

    @Value("${app.secret.salt}")
    private String secretSalt;
    @Value("${app.website.link}")
    private String websiteLink;

    private final HtmlToPdfService htmlToPdfService;
    private final ThymeleafService thymeleafService;
    private final ZXingService zXingService;

    @Autowired
    public CertificateManagerService(HtmlToPdfService htmlToPdfService, ThymeleafService thymeleafService, ZXingService zXingService) {
        this.htmlToPdfService = htmlToPdfService;
        this.thymeleafService = thymeleafService;
        this.zXingService = zXingService;
    }

    public void HandleApplyRequest(ApplyCertRequest request) {
        // TODO: Replace with actual student and Event names when they are ready
        String StudentName = "john doe"; // StudentRepo.get(request.getStudentId());
        String EventName = "HTML Basics"; // EventRepo.get(request.getEventId());
        String stringToHash = StudentName + "|" + EventName + "|" + secretSalt;

        /// Step 1: Create the hash
        String hashedString = SHA256(stringToHash);

        /// Step 2: Create the QR code
        String url = websiteLink + "/cert/verify/" + hashedString;
        String qrCode = zXingService.generateQRCodeBase64(url, 512, 512);

        /// Step 3: Generate the certificate
        String certHTML = thymeleafService.generateHtml(StudentName, EventName, qrCode);
        htmlToPdfService.convertHtmlToPdf(certHTML, "certificate.pdf");
    }
}
