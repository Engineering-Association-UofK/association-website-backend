package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.CloudinaryRequestModel;
import edu.uofk.ea.association_website_backend.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sign")
public class CloudinaryController {

    private final CloudinaryService service;

    @Autowired
    public CloudinaryController(CloudinaryService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'BLOG_MANAGER', 'SUPER_ADMIN')")
    public CloudinaryRequestModel signRequest(@RequestBody CloudinaryRequestModel request) {
        return service.validateAndSign(request);
    }
}
