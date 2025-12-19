package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.BlogPostModel;
import edu.uofk.ea.association_website_backend.repository.BlogPostRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BlogPostService {
    
    private BlogPostRepo repo;
    
    @Autowired
    public BlogPostService(BlogPostRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public void save(BlogPostModel post){
        post.setCreatedAt(Instant.now());
        post.setUpdatedAt(Instant.now());

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
