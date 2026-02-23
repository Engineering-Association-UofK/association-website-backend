package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.BlogPostModel;
import edu.uofk.ea.association_website_backend.model.BlogPostRequest;
import edu.uofk.ea.association_website_backend.service.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAnyRole('BLOG_MANAGER', 'SUPER_ADMIN')")
    public void addBlog(@Valid @RequestBody BlogPostRequest blogRequest, Authentication authentication){
        String username = authentication.getName();
        service.save(blogRequest, username);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('BLOG_MANAGER', 'SUPER_ADMIN')")
    public void UpdateBlog(@Valid @RequestBody BlogPostRequest blogRequest){
        service.update(blogRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('BLOG_MANAGER', 'SUPER_ADMIN')")
    public void deleteBlog(@PathVariable int id){
        service.delete(id);
    }
}
