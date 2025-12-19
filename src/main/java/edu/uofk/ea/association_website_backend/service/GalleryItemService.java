package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.GalleryItemModel;
import edu.uofk.ea.association_website_backend.repository.GalleryItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class GalleryItemService {

    private GalleryItemRepo repo;

    @Autowired
    public GalleryItemService(GalleryItemRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public void save(GalleryItemModel post){
        post.setCreatedAt(Instant.now());
        repo.save(post);
    }

    public List<GalleryItemModel> getAll(){
        return repo.getAll();
    }

    @Transactional
    public void delete(int id){
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("Gallery item not found with ID:" + id);
        }
        repo.delete(id);
    }
}
