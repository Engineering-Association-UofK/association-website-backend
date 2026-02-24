package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.CloudinaryRequestModel;
import edu.uofk.ea.association_website_backend.model.activity.ActivityType;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.CloudinaryService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sign")
public class CloudinaryController {

    private final CloudinaryService service;
    private final ActivityService activityService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public CloudinaryController(CloudinaryService service, ActivityService activityService, AdminDetailsService adminDetailsService) {
        this.service = service;
        this.activityService = activityService;
        this.adminDetailsService = adminDetailsService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'BLOG_MANAGER', 'SUPER_ADMIN')")
    public CloudinaryRequestModel signRequest(@Valid @RequestBody CloudinaryRequestModel request, Authentication authentication, HttpServletRequest req) {
        CloudinaryRequestModel response = service.validateAndSign(request);
        int id = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.SIGN_CLOUDINARY_REQUEST, Map.of("publicId", req.getRemoteAddr()), id);
        return response;
    }
}
