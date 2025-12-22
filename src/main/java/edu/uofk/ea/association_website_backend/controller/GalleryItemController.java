package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.GalleryItemModel;
import edu.uofk.ea.association_website_backend.service.GalleryItemService;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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
    @PreAuthorize("hasRole('ADMIN', 'EDITOR')")
    public void addItem(@RequestBody GalleryItemModel item){
        repo.save(item);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN', 'EDITOR')")
    public void deleteItem(@PathVariable int id){
        repo.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(GenericNotFoundException e) {

        BaseErrorResponse error = new BaseErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                Instant.now().getEpochSecond()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
