package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemModel;
import edu.uofk.ea.association_website_backend.service.GalleryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
public class GalleryItemController {

    private final GalleryItemService repo;

    @Autowired
    public GalleryItemController(GalleryItemService repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<GalleryItemModel> getItems(){
        return repo.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void addItem(@RequestBody GalleryItemModel item){
        repo.save(item);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void deleteItem(@PathVariable int id){
        repo.delete(id);
    }
}
