package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.BlogPostModel;
import edu.uofk.ea.association_website_backend.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void addBlog(@RequestBody BlogPostModel Blog){
        service.save(Blog);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void UpdateBlog(@PathVariable int id, @RequestBody BlogPostModel post){
        post.setId(id);
        service.update(post);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void deleteBlog(@PathVariable int id){
        service.delete(id);
    }
}
