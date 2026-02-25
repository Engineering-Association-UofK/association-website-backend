package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.gallery.*;
import edu.uofk.ea.association_website_backend.model.storage.StoreType;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.GalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@Tag(
        name = "Gallery Management",
        description = "APIs for managing gallery items, including news and store content."
)
public class GalleryController {

    private final GalleryService service;
    private final ActivityService activityService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public GalleryController(GalleryService service, ActivityService activityService, AdminDetailsService adminDetailsService) {
        this.service = service;
        this.activityService = activityService;
        this.adminDetailsService = adminDetailsService;
    }

    @PostMapping
    @Operation(
            summary = "Add a new image to the gallery",
            description = "Stores metadata for a new image in the gallery."
    )
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void add(@RequestBody GalleryRequest request) {
        service.AddImage(request);
    }

    @GetMapping
    @Operation(
            summary = "Get all gallery items",
            description = "Retrieves a list of all gallery items."
    )
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public List<GalleryResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/news")
    @Operation(
            summary = "Get news items",
            description = "Retrieves a list of news items."
    )
    public List<NewsResponse> getNews() {
        return service.getNews();
    }

    @PostMapping("/news")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Add a new news state",
            description = "Creates a new news state from an existing storage image."
    )
    public void makeNews(@RequestBody NewsRequest request) {
        service.makeNews(request.getStorageId(), request.getAlt());
    }

    @DeleteMapping("/news/{storageId}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Delete a news state",
            description = "Deletes a news state by its Storage ID. The actual image is not deleted."
    )
    public void deleteNews(@PathVariable int storageId) {
        service.deleteNews(storageId);
    }

    @DeleteMapping("/cleanup")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(
            summary = "Cleanup unused images",
            description = "Deletes images that are not linked to any entity."
    )
    public void deleteUnusedImages() {
        service.deleteUnusedImages(StoreType.IMAGE);
    }
}
