package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.EntityType;
import edu.uofk.ea.association_website_backend.model.admin.AdminModel;
import edu.uofk.ea.association_website_backend.model.blog.BlogPostModel;
import edu.uofk.ea.association_website_backend.model.blog.BlogPostRequest;
import edu.uofk.ea.association_website_backend.repository.AdminRepo;
import edu.uofk.ea.association_website_backend.repository.BlogPostRepo;
import edu.uofk.ea.association_website_backend.repository.StorageRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BlogPostService {
    
    private final BlogPostRepo repo;
    private final AdminRepo adminRepo;
    private final StorageManagementService storageService;

    @Autowired
    public BlogPostService(BlogPostRepo repo, AdminRepo adminRepo, StorageManagementService storageService) {
        this.repo = repo;
        this.adminRepo = adminRepo;
        this.storageService = storageService;
    }

    @Transactional
    public void save(BlogPostRequest blogRequest, String username){
        AdminModel admin = adminRepo.findByUsername(username);
        if (admin == null) {
            throw new GenericNotFoundException("Admin not found with username:" + username);
        }

        BlogPostModel post = new BlogPostModel(
                blogRequest.getTitle(),
                blogRequest.getContent(),
                blogRequest.getStatus(),
                admin.getId()
        );

        int id = repo.save(post);

        if (blogRequest.getImage() != null) {
            storageService.linkImageToEntity(blogRequest.getImage().getPublicId(), blogRequest.getImage().getUrl(), EntityType.BLOG_POST, id);
            post.setImageLink(blogRequest.getImage().getUrl());
            repo.update(post);
        }

    }

    public BlogPostModel findById(int id){
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("Blog post not found with ID:" + id);
        }
        return repo.findById(id);
    }


    public List<BlogPostModel> getAll(){
        return repo.getAll();
    }

    @Transactional
    public void update(BlogPostRequest blogRequest){
        BlogPostModel model = repo.findById(blogRequest.getId());
        if (model == null) {
            throw new GenericNotFoundException("Blog post not found with ID:" + blogRequest.getId());
        }
        if (blogRequest.getImage() != null) {
            storageService.linkImageToEntity(
                    blogRequest.getImage().getPublicId(),
                    blogRequest.getImage().getUrl(),
                    EntityType.BLOG_POST,
                    blogRequest.getId()
            );
            model.setImageLink(blogRequest.getImage().getUrl());
        }

        model.setTitle(blogRequest.getTitle());
        model.setContent(blogRequest.getContent());
        model.setStatus(blogRequest.getStatus());

        model.setUpdatedAt(Instant.now());
        repo.update(model);
    }

    @Transactional
    public void delete(int id){
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("Blog post not found with ID:" + id);
        }
        storageService.unlinkImageFromEntity(EntityType.BLOG_POST, id);
        repo.delete(id);
    }
}
