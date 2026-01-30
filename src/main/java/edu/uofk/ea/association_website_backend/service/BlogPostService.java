package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.BlogPostModel;
import edu.uofk.ea.association_website_backend.repository.AdminRepo;
import edu.uofk.ea.association_website_backend.repository.BlogPostRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BlogPostService {
    
    private BlogPostRepo repo;
    private AdminRepo adminRepo;
    
    @Autowired
    public BlogPostService(BlogPostRepo repo, AdminRepo adminRepo) {
        this.repo = repo;
        this.adminRepo = adminRepo;
    }

    @Transactional
    public void save(BlogPostModel post, String username){
        post.setCreatedAt(Instant.now());
        post.setUpdatedAt(Instant.now());

        if (adminRepo.findByUsername(username) == null) {
            throw new GenericNotFoundException("Admin not found with username:" + username);
        }
        post.setAuthorId(adminRepo.findByUsername(username).getId());
        if (post.getStatus() == null) post.setStatus(BlogPostModel.Status.draft);

        repo.save(post);
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
    public void update(BlogPostModel post){
        if (repo.findById(post.getId()) == null) {
            throw new GenericNotFoundException("Blog post not found with ID:" + post.getId());
        }

        post.setUpdatedAt(Instant.now());
        repo.update(post);
    }

    @Transactional
    public void delete(int id){
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("Blog post not found with ID:" + id);
        }
        repo.delete(id);
    }
}
