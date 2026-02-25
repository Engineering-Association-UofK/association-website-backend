package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.blog.BlogPostModel;
import edu.uofk.ea.association_website_backend.model.blog.BlogPostRequest;
import edu.uofk.ea.association_website_backend.model.activity.ActivityType;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/blogs")
public class BlogPostController {

    private final BlogPostService service;
    private final ActivityService activityService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public BlogPostController(BlogPostService service, ActivityService activityService, AdminDetailsService adminDetailsService) {
        this.service = service;
        this.activityService = activityService;
        this.adminDetailsService = adminDetailsService;
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
    public void addBlog(@Valid @RequestBody BlogPostRequest Blog, Authentication authentication){
        String username = authentication.getName();
        service.save(Blog, username);
        int id = adminDetailsService.getId(username);
        activityService.log(ActivityType.CREATE_BLOG, Map.of("title", Blog.getTitle()), id);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('BLOG_MANAGER', 'SUPER_ADMIN')")
    public void UpdateBlog(@Valid @RequestBody BlogPostRequest blogRequest, Authentication authentication){
        service.update(blogRequest);
        int id = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.UPDATE_BLOG, Map.of("id", blogRequest.getId(), "title", blogRequest.getTitle()), id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('BLOG_MANAGER', 'SUPER_ADMIN')")
    public void deleteBlog(@PathVariable int id, Authentication authentication){
        service.delete(id);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.DELETE_BLOG, Map.of("id", id), adminId);
    }
}
