package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.annotations.RateLimited;
import edu.uofk.ea.association_website_backend.model.admin.AdminModel;
import edu.uofk.ea.association_website_backend.model.VerificationRequest;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminDetailsService service;

    @Autowired
    public AdminController(AdminDetailsService service) {
        this.service = service;
    }

    /// This endpoint is used to add new admin
    /// To make new admins the request body should have at least these fields:
    /// name - email - password
    /// without specifying a role it will default to viewer
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void addAdmin(@RequestBody AdminModel admin) {
        service.add(admin);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteAdmin(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<AdminModel> getAdmins() {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public AdminModel getAdmin(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping("/get-name/{id}")
    public String getAdminName(@PathVariable int id) {
        return service.getName(id);
    }

    @PostMapping("/login")
    @RateLimited(key = "login", capacity = 5, refillTokens = 5, refillDuration = 120)
    public String Login(@RequestBody AdminModel admin) {
        return service.login(admin);
    }

    @PostMapping("/send-code")
    @RateLimited(key = "otp", capacity = 3, refillTokens = 1, refillDuration = 60, exponentialBackoff = true)
    public void sendCode(@RequestBody VerificationRequest request) {
        service.sendCode(request.getName());
    }

    @PostMapping("/verify")
    @RateLimited(key = "verify", capacity = 5, refillTokens = 5, refillDuration = 60)
    public void verify(@RequestBody VerificationRequest request) {
        service.Verify(request.getName(), request.getCode());
    }
}
