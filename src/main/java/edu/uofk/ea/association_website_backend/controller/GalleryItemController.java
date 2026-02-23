package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.activity.ActivityType;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemModel;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemType;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryKeywordResponse;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.GalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gallery")
@Tag(
        name = "Gallery Management",
        description = """
                APIs for managing gallery items, including news and store content.
                
                **Store Items:** These are the items used to display on different sections on the website. They use **unique** keywords to identify them.
                You can add new ones through the api, and updating them through the Admin dashboard or directly from the page is possible as well,
                but you **cannot**, and should not, delete them through the api. Mistakes like that may make render issues.
                """
)
public class GalleryItemController {

    private final GalleryService service;
    private final ActivityService activityService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public GalleryItemController(GalleryService service, ActivityService activityService, AdminDetailsService adminDetailsService) {
        this.service = service;
        this.activityService = activityService;
        this.adminDetailsService = adminDetailsService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Get all gallery items",
            description = "Retrieves a list of all gallery items."
    )
    public List<GalleryItemModel> GetItems(){
        return service.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Add a new gallery item",
            description = "Creates a new gallery item entry. Store items must have a unique keyword."
    )
    public void AddItem(@RequestBody GalleryItemModel item, Authentication authentication){
        service.save(item);
        int id = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.CREATE_GALLERY, Map.of("title", item.getTitle(), "type", item.getType()), id);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Update a gallery item",
            description = "Updates an existing gallery item entry. Store items must have a unique keyword."
    )
    public void UpdateItem(@RequestBody GalleryItemModel item, Authentication authentication){
        service.update(item);
        int id = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.UPDATE_GALLERY, Map.of("id", item.getId(), "title", item.getTitle()), id);
    }

    @GetMapping("/open/{keyword}")
    @Operation(
            summary = "Get gallery item by keyword",
            description = "Retrieves a gallery item by its keyword."
    )
    public GalleryKeywordResponse GetItemsByKeyword(@PathVariable String keyword){
        return service.findByKeyword(keyword);
    }

    @GetMapping("/open/news")
    @Operation(
            summary = "Get news items",
            description = "Retrieves a list of news items."
    )
    public List<GalleryItemModel> GetNews(){
        return service.findByType(GalleryItemType.news);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Delete a gallery item",
            description = "Deletes a gallery item by its ID."
    )
    public void DeleteItem(@PathVariable int id, Authentication authentication){
        service.delete(id);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.DELETE_GALLERY, Map.of("id", id), adminId);
    }
}
