package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.BlogPostModel;
import edu.uofk.ea.association_website_backend.service.BlogPostService;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/blogs")
public class BlogPostController {

    private final BlogPostService service;

    @Autowired
    public BlogPostController(BlogPostService service) {
        this.service = service;
    }

    @GetMapping
    public List<BlogPostModel> getBlogs(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BlogPostModel getBlog(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    public void addBlog(@RequestBody BlogPostModel Blog){
        service.save(Blog);
    }

    @PutMapping("/{id}")
    public void UpdateBlog(@PathVariable int id, @RequestBody BlogPostModel post){
        post.setId(id);
        service.update(post);
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable int id){
        service.delete(id);
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
