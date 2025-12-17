package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.BlogPostModel;
import edu.uofk.ea.association_website_backend.repository.BlogPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blogs")
public class BlogPostController {

    private final BlogPostRepo repo;

    @Autowired
    public BlogPostController(BlogPostRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<BlogPostModel> getBlogs(){
        return repo.getAll();
    }

    @GetMapping("/{id}")
    public BlogPostModel getBlog(@PathVariable int id) {
        return repo.findById(id);
    }

    @PostMapping
    public void addBlog(@RequestBody BlogPostModel Blog){
        repo.save(Blog);
    }

    @PutMapping("/{id}")
    public void UpdateBlog(@PathVariable int id, @RequestBody BlogPostModel post){
        post.setId(id);
        repo.update(post);
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable int id){
        repo.delete(id);
    }
}
