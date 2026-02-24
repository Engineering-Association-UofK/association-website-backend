package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.annotations.RateLimited;
import edu.uofk.ea.association_website_backend.model.activity.ActivityType;
import edu.uofk.ea.association_website_backend.model.admin.*;
import edu.uofk.ea.association_website_backend.model.authentication.VerificationRequest;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminDetailsService service;
    private final ActivityService activityService;

    @Autowired
    public AdminController(AdminDetailsService service, ActivityService activityService) {
        this.service = service;
        this.activityService = activityService;
    }

    /// This endpoint is used to add new admin
    /// To make new admins the request body should have at least these fields:
    /// name - email - password - roles
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER', 'SUPER_ADMIN')")
    public void addAdmin(@Valid @RequestBody AdminRequest admin, Authentication authentication) {
        service.add(admin);
        int id = service.getId(authentication.getName());
        activityService.log(ActivityType.CREATE_ADMIN, Map.of("name", admin.getName(), "email", admin.getEmail()), id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER', 'SUPER_ADMIN')")
    public void deleteAdmin(@PathVariable int id, Authentication authentication) {
        service.delete(id);
        int currentAdminId = service.getId(authentication.getName());
        activityService.log(ActivityType.DELETE_ADMIN, Map.of("id", id, "name", service.getName(id)), currentAdminId);
    }

    /// This endpoint is used to update admin profile
    /// The updatable fields are: name, email, password
    ///
    /// Each admin can update their own profile even if they do not have the ROLE_ADMIN
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER', 'SUPER_ADMIN')")
    public void updateAdmin(@Valid @RequestBody AdminRequest admin, Authentication authentication) {
        service.updateProfile(admin);
        int id = service.getId(authentication.getName());
        activityService.log(ActivityType.UPDATE_ADMIN_PROFILE, Map.of("name", admin.getName(), "email", admin.getEmail()), id);
    }

    @PutMapping("/update-password")
    public void updatePassword(@Valid @RequestBody UpdatePasswordRequest request, Authentication authentication) {
        String username = authentication.getName();
        service.updatePassword(request, username);
        int id = service.getId(username);
        activityService.log(ActivityType.CHANGE_PASSWORD, null, id);
    }

    @PutMapping("/update-email")
    public void updateEmail(@Valid @RequestBody UpdateEmailRequest request, Authentication authentication) {
        String username = authentication.getName();
        service.updateEmail(request, username);
        int id = service.getId(username);
        activityService.log(ActivityType.UPDATE_EMAIL, Map.of("email", request.getNewEmail()), id);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER', 'SUPER_ADMIN')")
    public List<AdminModel> getAdmins() {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER', 'SUPER_ADMIN')")
    public AdminModel getAdmin(@PathVariable int id) {
        return service.get(id);
    }

    ///  This is left without a PreAuthorize on purpose
    /// It is used to get names of authors
    @GetMapping("/get-name/{id}")
    public String getAdminName(@PathVariable int id) {
        return service.getName(id);
    }

    @PostMapping("/login")
    @RateLimited(key = "login", capacity = 5, refillTokens = 5, refillDuration = 120)
    public String login(@Valid @RequestBody LoginRequest admin, HttpServletRequest request) {
        String token = service.login(admin);
        if (token != null) {
            int id = service.getId(admin.getName());
            activityService.log(ActivityType.LOGIN, Map.of("name", admin.getName(), "ip", request.getRemoteAddr()), id);
        }
        return token;
    }

    @PostMapping("/send-code")
    @RateLimited(key = "otp", capacity = 3, refillTokens = 1, refillDuration = 60, exponentialBackoff = true)
    public void sendCode(@Valid @RequestBody VerificationRequest request) {
        service.sendCode(request.getName());
    }

    @PostMapping("/verify")
    @RateLimited(key = "verify", capacity = 5, refillTokens = 5, refillDuration = 60)
    public void verify(@Valid @RequestBody VerificationRequest request) {
        service.verify(request.getName(), request.getCode());
    }
}
