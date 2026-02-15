package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemModel;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemType;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryKeywordResponse;
import edu.uofk.ea.association_website_backend.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
public class GalleryItemController {

    private final GalleryService repo;

    @Autowired
    public GalleryItemController(GalleryService repo) {
        this.repo = repo;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public List<GalleryItemModel> GetItems(){
        return repo.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void AddItem(@RequestBody GalleryItemModel item){
        repo.save(item);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void UpdateItem(@RequestBody GalleryItemModel item){
        repo.update(item);
    }

    @GetMapping("/open/{keyword}")
    public GalleryKeywordResponse GetItemsByKeyword(@PathVariable String keyword){
        return repo.findByKeyword(keyword);
    }

    @GetMapping("/open/news")
    public List<GalleryItemModel> GetNews(){
        return repo.findByType(GalleryItemType.news);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void DeleteItem(@PathVariable int id){
        repo.delete(id);
    }
}
