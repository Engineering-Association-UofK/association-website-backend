package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.GalleryItemModel;
import edu.uofk.ea.association_website_backend.repository.GalleryItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
public class GalleryItemController {

    private final GalleryItemRepo repo;

    @Autowired
    public GalleryItemController(GalleryItemRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<GalleryItemModel> getItems(){
        return repo.getAll();
    }

    @PostMapping
    public void addItem(@RequestBody GalleryItemModel item){
        repo.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable int id){
        repo.delete(id);
    }


}
