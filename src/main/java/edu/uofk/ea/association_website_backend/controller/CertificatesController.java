package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.annotations.RateLimited;
import edu.uofk.ea.association_website_backend.model.admin.AdminModel;
import edu.uofk.ea.association_website_backend.model.authentication.VerificationRequest;
import edu.uofk.ea.association_website_backend.model.certificates.ApplyCertRequest;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.certificates.CertificateManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cert")
public class CertificatesController {

    private final CertificateManagerService manager;

    @Autowired
    public CertificatesController(CertificateManagerService manager) {
        this.manager = manager;
    }

    @PostMapping("/apply")
    public void applyToCert(@RequestBody ApplyCertRequest request) {
        manager.HandleApplyRequest(request);
    }
}
