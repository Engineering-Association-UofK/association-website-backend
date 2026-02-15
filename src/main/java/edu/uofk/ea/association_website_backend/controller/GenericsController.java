package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.generics.GenericBatchRequest;
import edu.uofk.ea.association_website_backend.model.generics.GenericGetRequest;
import edu.uofk.ea.association_website_backend.model.generics.GenericResponse;
import edu.uofk.ea.association_website_backend.model.generics.GenericRequest;
import edu.uofk.ea.association_website_backend.service.GenericsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/generics")
@Tag(
        name = "Generics Management",
        description = "APIs for managing generic translations, which is every content on the page that was static before."
)
public class GenericsController {

    private final GenericsService service;

    @Autowired
    public GenericsController(GenericsService service) {
        this.service = service;
    }

    @PostMapping("new")
    @Operation(
            summary = "Save a new generic content entry",
            description = "Creates a new generic model with a unique keyword and its first translation."
    )
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void SaveGeneric(@RequestBody GenericRequest request) {
        service.Save(request);
    }

    @PostMapping("new-all")
    @Operation(
            summary = "Save multiple generic content entries",
            description = "Creates multiple new generic models, each with a unique keyword and its first translation."
    )
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void SaveAllGeneric(@RequestBody List<GenericRequest> request) {
        service.SaveAll(request);
    }

    @PostMapping("get")
    @Operation(
            summary = "Get generic content",
            description = "Retrieves the title and body for a specific keyword in the requested language. Falls back to English if the requested language is not found."
    )
    public GenericResponse GetGeneric(@RequestBody GenericGetRequest request) {
        return service.getGeneric(request);
    }

    @PostMapping("get-batch")
    @Operation(
            summary = "Get multiple generic content entries",
            description = "Retrieves a list of titles and bodies for a list of keywords in the requested language. Falls back to English if the requested language is not found."
    )
    public List<GenericResponse> GetBatchGeneric(@RequestBody GenericBatchRequest request) {
        return service.GetBatchGeneric(request);
    }

    @PutMapping("update")
    @Operation(
            summary = "Update generic content",
            description = "Updates an existing translation or adds a new translation for an existing keyword."
    )
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void UpdateGeneric(@RequestBody GenericRequest request) {
        service.update(request);
    }

    @PutMapping("update-all")
    @Operation(

    )
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void UpdateAllGeneric(@RequestBody List<GenericRequest> request) {
        service.updateAll(request);
    }

    @DeleteMapping("delete/{id}")
    @Operation(
            summary = "Delete generic content",
            description = "Deletes a generic content entry and all its associated translations by its ID."
    )
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void DeleteFaq(@PathVariable int id) {
        service.delete(id);
    }
}
