package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.AdminModel;
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
    public void sendCode(@RequestParam String username) {
        service.sendCode(username);
    }

    @PostMapping("/verify")
    public void verify(@RequestParam String username, @RequestParam String code) {
        service.Verify(username, code);
    }
}
