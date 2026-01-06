package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.annotations.RateLimited;
import edu.uofk.ea.association_website_backend.model.AdminModel;
import edu.uofk.ea.association_website_backend.model.VerificationRequest;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminDetailsService service;

    @Autowired
    public AdminController(AdminDetailsService service) {
        this.service = service;
    }

    @PostMapping("/login")
    @RateLimited(key = "login", capacity = 5, refillTokens = 5, refillDuration = 120)
    public String Login(@RequestBody AdminModel admin) {
        return service.login(admin);
    }

    /// This endpoint is used to add new admin
    /// To make new admins you the request body should have these fields:
    /// name - password - email - role
    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void register(@RequestBody AdminModel admin) {
        service.register(admin);
    }

    @PostMapping("/send-code")
    @RateLimited(key = "otp", capacity = 1, refillTokens = 1, refillDuration = 60, exponentialBackoff = true)
    public void sendCode(@RequestBody VerificationRequest request) {
        service.sendCode(request.getName());
    }

    @PostMapping("/verify")
    @RateLimited(key = "verify", capacity = 5, refillTokens = 5, refillDuration = 60)
    public void verify(@RequestBody VerificationRequest request) {
        service.Verify(request.getName(), request.getCode());
    }
}
